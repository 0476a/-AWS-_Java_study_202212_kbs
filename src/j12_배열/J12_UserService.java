package j12_배열;

// 풀이
import java.util.Scanner;

// 기능을 실행하기 위한 용도 (비지니스 로직)
public class J12_UserService {

	private Scanner scanner; // 전역 변수 : 모든 공간에 사용할 수 있기 때문에 밖으로 빼둠
	// scanner = new Scanner(System.in); // 생성을 하지 않아도 클래스가 정의 될때 부터 할당 됨!
	private J12_UserRepository userRepository;

//	public J12_UserService(Scanner scanner) { // 결합도가 낮은 코드 
//		this.scanner = scanner;
//		userRepository = new J12_UserRepository(null); // 결합도 높은 코드
//	}

	public J12_UserService(J12_UserRepository userRepository) { // 결합도가 높은 코드 (생성자 호출이 있어야만 스캐너 호출)
		scanner = new Scanner(System.in); // 안에 넣은 이유 : 생성하면서 같이 생성되게끔 해줌.
											// 클래스를 사용하지 않을 때 메모리를 잡아먹기 때문
		this.userRepository = userRepository; // 결합도가 낮은 코드 (의존성 주입 코드)
	}

	public void run() {
		boolean loopFlag = true; // 지역변수로 쓰는 이유 : 하나 빠지면 다 빠져 나와서
		char select = '\0';

		while (loopFlag) {
			showMainMenu();
			select = inputSelect("메인");
			loopFlag = mainMenu(select);
		}

	}

	public void stop() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(500); // 반복 돌때 0.5초씩 쉰다.
				System.out.println("프로그램 종료중...(" + (i + 1) + "/10)");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("프로그램 종료");
	}

	private char inputSelect(String menuName) {
		System.out.print(menuName + " 메뉴 선택: ");
		char select = scanner.next().charAt(0); // next로 받고 // 문자열에서 charAt문자인덱스를
												// 찾아줘서 문자값으로 리턴해줌.
		scanner.nextLine(); // nextLine으로 엔터를 지워줌. 회원값을 받을 때 nextLine()으로 받기 때문
		return select;
	}

	private void showMainMenu() {
		System.out.println("========<< 메인메뉴 >>========");
		System.out.println("1. 회원 전체 조회");
		System.out.println("2. 회원 등록");
		System.out.println("3. 사용자이름으로 회원 조회");
		System.out.println("4. 회원 정보 수정");
		System.out.println("==============================");
		System.out.println("q. 프로그램 종료");
		System.out.println();
	}

	private void showUsers() {
		J12_User[] users = userRepository.getUserTable();

		System.out.println("========<< 회원 전체 조회 >>========");

		for (J12_User user : users) {
			System.out.println(user.toString());

		}
		System.out.println("=====================================");

	}

	private void registerUser() {
		J12_User user = new J12_User();

		System.out.println("========<< 회원 등록 >>========");
		System.out.print("사용자이름: ");
		user.setUsername(scanner.nextLine());

		System.out.print("비밀번호: ");
		user.setPassword(scanner.nextLine());

		System.out.print("성명: ");
		user.setName(scanner.nextLine());

		System.out.print("이메일: ");
		user.setEmail(scanner.nextLine());

		userRepository.saveUser(user);
	}

	private void showUser() {
		J12_User user = null;

		System.out.println("========<< 회원 조회 >>========");
		user = verifyUsername();

		if (user == null) {
			System.out.println("존재하지 않는 사용자이름입니다.");
			return;
		}

		System.out.println(user.toString());
		System.out.println("===============================");

	}

	private void changeUserInfo() {
		J12_User user = new J12_User();

		System.out.print("사용자 이름 입력: ");
		user.setUsername(scanner.nextLine());
		user = userRepository.findUserByUsername(user.getUsername());
		System.out.println("========<< 수정 메뉴 >>========");
		System.out.println("사용자이름: " + user.getUsername());
		System.out.println("===============================");
		System.out.println("1. 비밀번호 변경");
		System.out.println("2. 이름 변경");
		System.out.println("3. 이메일 변경");
		System.out.println("===============================");
		System.out.println("b. 뒤로가기");

	}

	private boolean mainMenu(char select) {
		boolean flag = true;

		if (isExit(select)) {
			flag = false;

		} else {
			if (select == '1') {
				showUsers();
			} else if (select == '2') {
				registerUser();
			} else if (select == '3') {
				showUser();
			} else if (select == '4') {
				updateUser();
			} else {
				System.out.println(getSelectedErrorMessage());
			}

		}
		System.out.println();

		return flag;
	}

	// 리팩터링 기법 : 기존 소스코드를 정리하는 개념
	private boolean isExit(char select) {
		return select == 'q' || select == 'Q';
	}

	private boolean isBack(char select) {
		return select == 'b' || select == 'B';
	}

	private String getSelectedErrorMessage() { // 메시지 내용을 다른 클래스에도 사용할 것이기 때문에
		return "###<< 잘못된 입력입니다. 다시 입력하세요. >>###";
		// 메시지를 수정할 때 용이 하기 때문에 String 사용
	}

	private J12_User verifyUsername() {
		String username = null;
		System.out.print("사용자이름: ");
		username = scanner.nextLine();
		return userRepository.findUserByUsername(username);

	}

	private void updateUser() {
		J12_User user = verifyUsername();
		if (user == null) {
			System.out.println("존재하지 않는 사용자이름입니다.");
			return; // 그냥 빠져나가 메인메뉴로 돌아감
		}

		boolean loopFlag = true;
		char select = '\0';

		while (loopFlag) {
			showUpdateMenu(user);
			select = inputSelect("수정");
			loopFlag = updateMenu(user, select);
		}

	}

	private boolean updateMenu(J12_User user, char select) {
		boolean flag = true;

		if (isBack(select)) {
			flag = false;

		} else {
			if (select == '1') {
				updatePassword(user);
			} else if (select == '2') {

			} else if (select == '3') {

			} else {
				System.out.println(getSelectedErrorMessage());
			}
		}
		System.out.println();

		return flag;

	}

	private void showUpdateMenu(J12_User user) {
		System.out.println("========<< 수정 메뉴 >>========");
		System.out.println("사용자이름: " + user.getUsername());
		System.out.println("===============================");
		System.out.println("1. 비밀번호 변경");
		System.out.println("2. 이름 변경");
		System.out.println("3. 이메일 변경");
		System.out.println("===============================");
		System.out.println("b. 뒤로가기");
		System.out.println();
	}

	private void updatePassword(J12_User user) {
		String oldPassword = null;
		String newPassword = null;
		String confirmPassword = null;

		System.out.println("=========<< 비밀번호 변경 >>=========");

		System.out.print("기존 비밀번호 입력: ");
		oldPassword = scanner.nextLine();

		if (!comparePassword(user.getPassword(), oldPassword)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

		System.out.print("새로운 비밀번호 입력: ");
		newPassword = scanner.nextLine();
		System.out.print("새로운 비밀번호 확인: ");
		confirmPassword = scanner.nextLine();

		if (!comparePassword(newPassword, confirmPassword)) {
			System.out.println("새로운 비밀번호가 서로 일치하지 않습니다.");
			return;
		}

		user.setPassword(newPassword);
		System.out.println("비밀번호 변경 완료.");

	}

	private boolean comparePassword(String prePassword, String nextPassword) {
		return prePassword.equals(nextPassword);
	}

}
