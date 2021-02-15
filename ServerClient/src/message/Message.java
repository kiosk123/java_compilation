package message;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Message {

	private final int BASE_LENGTH=70;
	private byte[] number;
	private byte[] gid;
	private byte[] sync;
	private byte[] reqAndRes;
	private byte[] result;
	private byte[] rule_id; 
	private byte[] node;
	private byte[] whiteSpace; 
	
	
	//number를 만든다
	public byte[] createNumber(Integer totalLength){
		number = new byte[8];
		Arrays.fill(number,(byte)'0');
		String totalLengthTemp = totalLength.toString();
		byte[] numberTemp = new byte[totalLengthTemp.length()];		
		for(int i = 0; i < numberTemp.length; i++){
			numberTemp[i] = (byte)totalLengthTemp.charAt(i);
		}		
		System.arraycopy(numberTemp,0,number,number.length-numberTemp.length,numberTemp.length);		
		return number;
	}
	
	//gid를 만든다.
	public byte[] createGID(){
		int startPos = 0;
		byte[] host = new byte[8];
		byte[] date = new byte[17];
		byte[] seq = new byte[5];
		byte[] zero = new byte[2];
		gid = new byte[32];
		
		Arrays.fill(zero,(byte)'0');
		
		//호스트 네임 처리
		String hostname=null;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		hostname = hostname.substring(0,8);
		Arrays.fill(host,(byte)' ');
		for(int i = 0;i < hostname.getBytes().length; i++){
			host[i] = hostname.getBytes()[i];
		}
		
		//날짜 처리
		Date curdate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateVal = sdf.format(curdate);
		for(int i = 0; i < dateVal.getBytes().length; i++){
			date[i] = dateVal.getBytes()[i];
		}
		
		//seq처리
		Arrays.fill(seq,(byte)'0');
		Integer sequence = 1; //임의의 시퀀스 값
		String sequenceString = sequence.toString();
		byte[] sequenceTemp = new byte[sequenceString.length()];
		for(int i=0; i<sequenceTemp.length; i++){
			sequenceTemp[i] = sequenceString.getBytes()[i];
		}
		
		
		System.arraycopy(sequenceTemp,0,seq,seq.length-sequenceTemp.length,sequenceTemp.length);
		System.arraycopy(host,0,gid,startPos,host.length);
		startPos += host.length;
		System.arraycopy(date,0,gid,startPos,date.length);
		startPos += date.length;
		System.arraycopy(seq,0,gid,startPos,seq.length);
		startPos += seq.length;
		System.arraycopy(zero,0,gid,startPos,zero.length);		
		return gid;
	}
	
	public byte[] createSync(String syncParam){
		sync = new byte[1];
		String syncVal = syncParam; 
		for(int i = 0; i<sync.length; i++){
			sync[i] = syncVal.getBytes()[i];
		}
		return sync;
	}
	
	public byte[] createReqAndRes(String reqAndResParam){
		reqAndRes = new byte[1];
		String reqAndResVal = reqAndResParam; 
		for(int i=0;i<reqAndRes.length;i++){
			reqAndRes[i] = reqAndResVal.getBytes()[i];
		}
		return reqAndRes;
	}
	
	public byte[] createResult(String resultParam){
		result = new byte[1];
		String resultVal=resultParam; //임의의 result 값
		for(int i = 0; i<result.length; i++){
			result[i]=resultVal.getBytes()[i];
		}
		return result;
	}
	
	public byte[] createRuleId(String ruleVal){
		rule_id=new byte[12];
		for(int i=0;i<rule_id.length;i++){
			rule_id[i]=ruleVal.getBytes()[i];
		}
		return rule_id;
	}
	
	public byte[] createNode(Integer nodeParam){
		node=new byte[2];
		Arrays.fill(node,(byte)'0');
		Integer nodeVal=nodeParam; //임의의 노드값
		String nodeValTemp=nodeVal.toString();
		
		byte[] nodeTemp=new byte[nodeValTemp.length()];
		for(int i=0;i<nodeTemp.length;i++){
			nodeTemp[i]=nodeValTemp.getBytes()[i];
		}
		System.arraycopy(nodeTemp, 0, node, node.length-nodeTemp.length, nodeTemp.length);	
		return node;
	}
	public byte[] createMessage(String ruleId, byte[] data) {
		int startPos=0;
		/*
		 * createGID 기본값 설정 data 조립 및 LENGTH 설정
		 */
	
		//전체 데이터 길이
		Integer totalLength = BASE_LENGTH+data.length;
		
		//메시지
		byte[] message = new byte[totalLength];
		
		//number
		number = createNumber(totalLength);		
		gid = createGID();		
		rule_id = createRuleId(ruleId);
		whiteSpace = new byte[13];
		
		Arrays.fill(whiteSpace,(byte)' '); // 공백값 세팅		
		System.arraycopy(number, 0, message, startPos, number.length);
		startPos += number.length;
		System.arraycopy(gid, 0, message, startPos, gid.length);
		startPos += gid.length;
		System.arraycopy(sync, 0, message, startPos, sync.length);
		startPos += sync.length;
		System.arraycopy(reqAndRes, 0, message, startPos, reqAndRes.length);
		startPos += reqAndRes.length;
		System.arraycopy(result, 0, message, startPos, result.length);
		startPos += result.length;
		System.arraycopy(rule_id, 0, message, startPos, rule_id.length);
		startPos += rule_id.length;
		System.arraycopy(node, 0, message, startPos, node.length);
		startPos += node.length;
		System.arraycopy(whiteSpace, 0, message, startPos, whiteSpace.length);
		startPos += whiteSpace.length;
		System.arraycopy(data, 0, message, startPos, data.length);
		startPos += data.length;
				
		return message;
	}
}
