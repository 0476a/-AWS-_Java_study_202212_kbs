package j24_람다;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Lambda2 {
	
	public static void main(String[] args) {
		
		// 1. Runnable - run()
		Runnable a = () -> System.out.println("실행");
		Runnable b = () -> {
			System.out.println("여");
			System.out.println("러");
			System.out.println("명");
			System.out.println("령");
			System.out.println("실");
			System.out.println("행");
		};
		
		a.run(); // 출력값 : 실행
		a.run(); // 출력값 : 실행
		b.run(); // 출력값 : 여
						//   러
						//   명
						//   령	
						//   실
						//   행
		
		// 2. Supplier<T> - T get()
		Supplier<LocalDate> c = () -> LocalDate.now();
		
		Supplier<String> d = () -> {

			LocalDate now = LocalDate.now();
			return now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
		};
		
		System.out.println(c.get()); // 출력값 : 2023-01-31
		System.out.println(d.get()); // 출력값 : 2023년 01월 31일
		
		// 3. Consumer<T> - void accept(T t)
		Consumer<String> e = name -> {
			System.out.println("이름: " + name);
			System.out.println("오늘 날짜: " + d.get());
		};
		
		e.accept("김삼겹");
		
		// 메소드 참조 표현식 ([인스턴스]::[메소드명 또는 new])
		Consumer<String> f = System.out :: println;
		f.accept("출력");
		
		List<String> names = new ArrayList<>();
		names.add("김삼겹");
		names.add("박김치");
		names.add("이무쇠");
		names.add("최파리");
		
		
		Consumer<String> g = name -> System.out.println("이름: " + name + "님");
		names.forEach(g);
//		names.forEach(name -> System.out.println("이름: " + name + "님"));
		
		// forEach문
		// this = names
//		default void forEach(Consumer<? super String> action) {
//	        Objects.requireNonNull(action);
//	        for (String t : this) {
//	            action.accept(t);
//	        }
//	    }
		
		
		names.forEach(name -> {
			System.out.println("이름을 출력합니다.");
			System.out.println("이름: " + name);
			System.out.println();
		});
		
		Map<String, String> userMap = new HashMap<>();
		userMap.put("username", "aaa");
		userMap.put("password", "1234");
		
		userMap.forEach((key, value) -> {
			System.out.println("key: " + key);
			System.out.println("value: " + value);
			System.out.println();
		});
		
		for(Entry<String, String> entry : userMap.entrySet()) {
			System.out.println("key: " + entry.getKey());
			System.out.println("value: " + entry.getValue());
			System.out.println();
		}
		
	}
	
}
