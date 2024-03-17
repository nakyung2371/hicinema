package com.himedia.hicinema.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import com.himedia.hicinema.member.Member;
import com.himedia.hicinema.member.MemberService;
import com.himedia.hicinema.movie.Movie;
import com.himedia.hicinema.movie.MovieService;
import com.himedia.hicinema.movie.loc.Location;
import com.himedia.hicinema.movie.loc.LocationService;
import com.himedia.hicinema.movie.schedule.Schedule;
import com.himedia.hicinema.movie.schedule.ScheduleService;
import com.himedia.hicinema.movie.seat.Seat;
import com.himedia.hicinema.movie.seat.SeatService;
import com.himedia.hicinema.movie.theater.Theater;
import com.himedia.hicinema.movie.theater.TheaterService;
import com.himedia.hicinema.pay.KakaoPay;
import com.himedia.hicinema.pay.KakaoPayApprovalVO;
import com.himedia.hicinema.payment.Payment;
import com.himedia.hicinema.payment.PaymentMovie;
import com.himedia.hicinema.payment.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reservation")
public class ReservationController {
    private final LocationService locationService;
    private final TheaterService theaterService;
    private final MovieService movieService;
    private final ScheduleService scheduleService;
    private final SeatService seatService;
    private final PaymentService paymentService;
    private final ReservationService reservationService;

    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;

    @GetMapping("/ticketing")
    public String reservationPage(Model model) {
        List<Location> locations = locationService.getList("O");
        List<Theater> theaters = theaterService.getList("O");
        List<Movie> movies = movieService.getMovieList("O");

        List<String> img_arr = new ArrayList<>();
        img_arr.add("/img_nsy/aespa_19207742.jpg");
        img_arr.add("/img_nsy/BabyShark.jpg");
        img_arr.add("/img_nsy/IfOnly_1920774.jpg");

        model.addAttribute("images", img_arr);
        model.addAttribute("title", "예매");
        model.addAttribute("locations", locations);
        model.addAttribute("theaters", theaters);
        model.addAttribute("movies", movies);
        return "user/ticketing";
    }

    @GetMapping("/ticketing/get_theaters")
    @ResponseBody
    public ResponseEntity<String> getTheaterList(String loc_id) {
        JsonObject jo = new JsonObject();
        try {
            Optional<Location> opt = locationService.getLocation(Long.valueOf(loc_id), "O");
            if(opt.isPresent()) {
                Location loc = opt.get();
                List<Theater> theaters = theaterService.getTheaterList(loc, "O");
                ObjectMapper om = new ObjectMapper();
                om.registerModule(new JavaTimeModule());
                String json = om.writeValueAsString(theaters);
                jo.addProperty("theaters", json);
                jo.addProperty("msg", "success");
            } else {
                jo.addProperty("msg", "error");
            }
        }catch (Exception e) {
            e.printStackTrace();
            jo.addProperty("msg", "error");
        }
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }

    @GetMapping("/ticketing/get_schedule")
    @ResponseBody
    public ResponseEntity<String> get_schedule(String theaterId, String movieCd, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime screeningDate) {
        JsonObject jo = new JsonObject();
        ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 또는 원하는 시간대로 설정
        ZonedDateTime zonedDateTime = screeningDate.atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
        LocalDateTime scd = zonedDateTime.toLocalDateTime();
        log.info("=============================================");
        System.out.println(theaterId);
        System.out.println(movieCd);
        System.out.println(scd);
        log.info("=============================================");
        try {
//            Movie movie = movieService.getMovie(movieCd, "O");
//            Theater theater = theaterService.getDetail(Long.valueOf(theaterId));
            List<Map<String, Object>> schedule = scheduleService.getUserPageSchedule(movieCd, scd, Long.valueOf(theaterId));
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            String json = om.writeValueAsString(schedule);
            jo.addProperty("msg", "success");
            jo.addProperty("schedules", json);
        } catch (Exception e){
            e.printStackTrace();
            jo.addProperty("msg", "error");
        }

        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }

    @GetMapping("/ticketing/seat")
    public String seatPage(Model model) {
        List<String> img_arr = new ArrayList<>();
        img_arr.add("/img_nsy/aespa_19207742.jpg");
        img_arr.add("/img_nsy/BabyShark.jpg");
        img_arr.add("/img_nsy/IfOnly_1920774.jpg");

        model.addAttribute("images", img_arr);
        model.addAttribute("title", "인원/좌석 선택");

        return "user/seat";
    }

    @GetMapping("/ticketing/get_seat_info")
    @ResponseBody
    public ResponseEntity<String> getSeatInfo(Schedule schedule) {
        JsonObject jo = new JsonObject();
        System.out.println(schedule);
        try {
            Schedule sch = scheduleService.getSchedule(schedule.getId());
            List<Seat> seats = seatService.getSeatList(sch);
            for(Seat seat : seats) {
                seat.setSchedule(null);
                seat.setScreen(null);
            }
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            String json = om.writeValueAsString(seats);
            jo.addProperty("msg", "success");
            jo.addProperty("seats", json);
        } catch (Exception e) {
            e.printStackTrace();
            jo.addProperty("msg", "error");
        }

        return new ResponseEntity<>(jo.toString(),HttpStatus.OK);
    }

    @PostMapping("/ticketing/order/info")
    public String paymentInfo(@RequestParam MultiValueMap<String, String> map, Model model, HttpSession session) {
        System.out.println(map.toString());
        session.setAttribute("info", map);
        model.addAttribute("info", map);
        return "redirect:/reservation/ticketing/order";
    }

    @GetMapping("/ticketing/order")
    public String orderPage(Model model, HttpSession session) {
        List<String> img_arr = new ArrayList<>();
        img_arr.add("/img_nsy/aespa_19207742.jpg");
        img_arr.add("/img_nsy/BabyShark.jpg");
        img_arr.add("/img_nsy/IfOnly_1920774.jpg");
        Map<String, String> info = (Map<String, String>) session.getAttribute("info");
//        session.removeAttribute("info");
        Map<String, String> map = new HashMap<>();
        for(String k : info.keySet()) {
            String v = "";
            Object val = info.get(k);
            if(val instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) val;
                if (!arrayList.isEmpty()) {
                    v = arrayList.get(0).toString();
                }
            } else {
                v = info.get(k);
            }
            map.put(k, v);
        }
        System.out.println(map);
        Schedule schedule = scheduleService.getSchedule(Long.valueOf(map.get("schedule_id")));
        System.out.println(schedule);

        model.addAttribute("images", img_arr);
        model.addAttribute("title", "결제 정보");
        model.addAttribute("schedule", schedule);
        model.addAttribute("info", map);
        return "user/ticketing_order";
    }

    @RequestMapping("/complete")
    public String kakaopaySuccess(@RequestParam("pg_token") String pg_token, Model model, Principal principal) {
        log.info("......................kakaoPaySuccess get......................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        KakaoPayApprovalVO payInfo = new KakaoPayApprovalVO();
        Long id = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                payInfo = kakaopay.kakaoPayInfo(pg_token);
                Member member = reservationService.getMember(principal.getName());
                id = paymentService.savePayment(payInfo, member.getId());
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("info", payInfo);
        model.addAttribute("payId", id);
        return "user_test/kakaoPaySuccess";
    }

    @PostMapping("/paymentMovie/post")
    @ResponseBody
    public ResponseEntity<String> paymentMoviePost(PaymentMovie pm, Principal principal) {
        JsonObject jo = new JsonObject();
        try{
            Member member = reservationService.getMember(principal.getName());
            paymentService.savePaymentMovie(pm, member.getId());
            jo.addProperty("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            jo.addProperty("msg", "error");
        }

        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }

    @RequestMapping("/SuccessFail")
    public String kakaoPaySuccessFail() {
        return "user_test/kakaoPaySuccessFail";
    }

    @RequestMapping("/Cancel")
    public String kakaoPayCancel() {
        return "user_test/kakaoPayCancel";
    }

}
