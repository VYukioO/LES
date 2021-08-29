package br.edu.fatec.les.facade;

public class Mensagem {
	private String msg;
	private MensagemStatus msgStatus;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public MensagemStatus getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(MensagemStatus msgStatus) {
		this.msgStatus = msgStatus;
	}

}
