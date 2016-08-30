package function.flag;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import tool.ListTool;
import function.Function;
import function.FunctionResult;
import function.ResultType;
import function.tool.CrossResult;
import function.tool.CrossTool;
/**判断双线交叉方向方法*/
public class CrossFunction extends Function {
	/**主动交叉股票代码*/
	public String siid1;
	/**主动数据属性，具体见ListTool的switch中*/
	public String attribute1;
	/**被动交叉股票代码*/
	public String siid2;
	/**被动数据属性*/
	public String attribute2;
	/**往前推的天数*/
	public int day;
	
	public CrossFunction(CrossVO vo)
	{
		this.function="Cross";
		this.siid1=vo.siid1;
		this.attribute1=vo.attribute1;
		this.siid2=vo.siid2;
		this.attribute2=vo.attribute2;
		this.day=vo.day;
		this.resultUpI=vo.resultUpI;
		this.resultDownI=vo.resultDownI;
		this.resultUpO=vo.resultUpO;
		this.resultDownO=vo.resultDownO;
	}
	@Override
	public FunctionResult getResult(Date today) {
		Date start=new Date(today.getTime()-day*24*60*60*1000);
		Date end=today;
		List<Double> activelist=new ListTool().getList(siid1,attribute1,start,end);
		List<Double> passivelist=new ListTool().getList(siid2,attribute2,start,end);
		CrossResult crossResult=new CrossTool(activelist,passivelist).cross();
		FunctionResult result=new FunctionResult();
		result.location.add(ResultType.INT);
		result.rI=crossResult.cross;
		return result;
	}
	
	public String getSiid1() {
		return siid1;
	}
	public void setSiid1(String siid1) {
		this.siid1 = siid1;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getSiid2() {
		return siid2;
	}
	public void setSiid2(String siid2) {
		this.siid2 = siid2;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	@Override
	public String toString() {
		return "CrossFunction [siid1=" + siid1 + ", attribute1=" + attribute1
				+ ", siid2=" + siid2 + ", attribute2=" + attribute2 + ", day="
				+ day + "]";
	}
}
