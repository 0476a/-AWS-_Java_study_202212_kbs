package j07_반복;

import java.util.Scanner;

// 내가한거
public class Loop2 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int startNumber = 0;
		int endNumber = 0;
		
		
		System.out.print("시작: ");
		startNumber = scanner.nextInt();
		
		System.out.print("끝: ");
		endNumber = scanner.nextInt();
		
		int total = 0;
		
		for (int i = 0; i < endNumber - 1; i++) {
			total += startNumber + i; 
			
		}
		
		System.out.println("총합: " + total);

	}

}
