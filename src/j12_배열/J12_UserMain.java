package j12_배열;

public class J12_UserMain {

	public static void main(String[] args) {
		J12_User[] users = new J12_User[0];
		J12_User[] users2 = new J12_User[100];
		
		J12_UserRepository userRepository = new J12_UserRepository(users);
		J12_UserRepository userRepository2 = new J12_UserRepository(users2); // User를 사용 해야해서 받아줌
		
		J12_UserService service = new J12_UserService(userRepository); // Repository를 사용 해야해서 받아줌
		
		service.run();
		service.stop();

	}

}
