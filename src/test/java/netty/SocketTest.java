package netty;



import java.net.Socket;


public class SocketTest {
	public static void main(String[] args) throws Exception {
		String encode = CryptoUtil.encode("9527");
		System.out.println(encode);
		String encode1 = CryptoUtil.encode("9528");
		System.out.println(encode1);
	}
	private static void send(Socket socket, int opcode, Object protocal) throws Exception {
		//byte[] body = JSON.toJSONString(protocal).getBytes();
		//// 包长是一个Short，就是协议编号的长度+协议的长度
		//socket.getOutputStream().write(ByteArrayUtils.toByteArray((short) (body.length + 4)));
		//socket.getOutputStream().write(ByteArrayUtils.toByteArray(opcode));
		//socket.getOutputStream().write(body);
		//socket.getOutputStream().flush();
	}
}