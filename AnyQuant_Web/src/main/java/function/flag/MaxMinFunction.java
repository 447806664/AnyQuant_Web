package function.flag;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tool.ListTool;
import function.Function;
import function.FunctionResult;
import function.ResultType;

/**配合波浪理论获取曲线的多个极值点*/
public class MaxMinFunction extends Function{
	/**数据属性名*/
	public String attribute;
	public Function attributeF;
	
	/**要求极大值还是极小值*/
	public int mm;//极大值为1，极小值为0
	public Function mmF;
	
	/**需要几个值*/
	public int num;//几个
	public Function numF;
	
	public MaxMinFunction()
	{
		this.function="MaxMin";
	}
	
	public MaxMinFunction(String attribute, int mm,int num) {
		this.function="MaxMin";
		this.attribute = attribute;
		this.mm = mm;
		this.num = num;
	}

	public MaxMinFunction(String attribute, Function attributeF, int mm,
			Function mmF, int num, Function numF) {
		this.function="MaxMin";
		this.attribute = attribute;
		this.attributeF = attributeF;
		this.mm = mm;
		this.mmF = mmF;
		this.num = num;
		this.numF = numF;
	}

	public MaxMinFunction(MaxMinVO vo)
	{
		this.function="MaxMin";
		this.siid=vo.siid;
		this.siidF=vo.siidF;
		this.num=vo.num;
		this.numF=vo.numF;
		this.mm=vo.mm;
		this.mmF=vo.mmF;
		this.resultUpI=vo.resultUpI;
		this.resultUpIF=vo.resultUpIF;
		this.resultDownI=vo.resultDownI;
		this.resultDownIF=vo.resultDownIF;
		this.resultUpO=vo.resultUpO;
		this.resultUpOF=vo.resultUpOF;
		this.resultUpO=vo.resultDownO;
		this.resultUpOF=vo.resultDownOF;
	}
	@Override
	public FunctionResult getResult(Date date) {
		FunctionResult result=new FunctionResult();
		result.location.add(ResultType.LONGLIST.getCode());
		result.location.add(ResultType.DOUBLELIST.getCode());
		result.rLL=new ArrayList<Long>();
		result.rLD=new ArrayList<Double>();
		List<Long> listL=new ListTool().getDate(siid,new Date(0),date);
		List<Double> listD=new ListTool().getList(siid,attribute,new Date(0),date);
		if(mm==1)
		{
			int count=0;
			for(int i=listL.size()-1;i>=0&&count<num;i--)
			{
				if(i==listL.size()-1)
				{
					if(listD.get(i)>listD.get(i-1))
					{
						result.rLL.add(listL.get(i));
						result.rLD.add(listD.get(i));
						count++;
					}
				}
				else if(i==0)
				{
					if(listD.get(i)>listD.get(i+1))
					{
						result.rLL.add(listL.get(i));
						result.rLD.add(listD.get(i));
						count++;
					}
				}
				else{
					if(listD.get(i)>listD.get(i+1)&&listD.get(i)>listD.get(i-1))
					{
						result.rLL.add(listL.get(i));
						result.rLD.add(listD.get(i));
						count++;
					}
				}
			}
		}
		else if(mm==0)
		{
			int count=0;
			for(int i=listL.size()-1;i>=0&&count<num;i++)
			{
				if(i==listL.size()-1)
				{
					if(listD.get(i)<listD.get(i-1))
					{
						result.rLL.add(listL.get(i));
						result.rLD.add(listD.get(i));
						count++;
					}
				}
				if(i==0)
				{
					if(listD.get(i)<listD.get(i+1))
					{
						result.rLL.add(listL.get(i));
						result.rLD.add(listD.get(i));
						count++;
					}
				}
				if(listD.get(i)<listD.get(i+1)&&listD.get(i)<listD.get(i-1))
				{
					result.rLL.add(listL.get(i));
					result.rLD.add(listD.get(i));
					count++;
				}
			}
		}
		return result;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Function getAttributeF() {
		return attributeF;
	}
	public void setAttributeF(Function attributeF) {
		this.attributeF = attributeF;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public Function getMmF() {
		return mmF;
	}
	public void setMmF(Function mmF) {
		this.mmF = mmF;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Function getNumF() {
		return numF;
	}
	public void setNumF(Function numF) {
		this.numF = numF;
	}
	@Override
	public String toString() {
		return "MaxMinFunction [attribute=" + attribute + ", attributeF="
				+ attributeF + ", mm=" + mm + ", mmF=" + mmF + ", num=" + num
				+ ", numF=" + numF + "]";
	}
}