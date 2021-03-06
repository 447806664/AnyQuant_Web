package function.tool;

import java.sql.Date;
import java.util.ArrayList;

import function.Function;
import function.FunctionResult;
import function.ResultType;

public class AddFunction extends Function {
	public double v1;
	public Function v1F;
	public double v2;
	public Function v2F;
	
	public AddFunction()
	{
		super();
		this.function="Add";
		this.siid=null;
		this.siidF=null;
		this.resultDownI=null;
		this.resultDownIF=null;
		this.resultDownO=null;
		this.resultDownOF=null;
		this.resultUpI=null;
		this.resultUpIF=null;
		this.resultUpO=null;
		this.resultUpOF=null;
		this.v1=0;
		this.v1F=null;
		this.v2=0;
		this.v2F=null;
	}
	public AddFunction(double v1,double v2)
	{
		this();
		this.function="Add";
		this.v1=v1;
		this.v2=v2;
	}
	public AddFunction(double v1, Function v1f, double v2, Function v2f) {
		this();
		this.function="Add";
		this.v1 = v1;
		this.v1F = v1f;
		this.v2 = v2;
		this.v2F = v2f;
	}
	public AddFunction(AddVO vo)
	{
		this();
		this.function="Add";
		this.resultDownI=vo.resultDownI;
		this.resultDownIF=vo.resultDownIF;
		this.resultDownO=vo.resultDownO;
		this.resultDownOF=vo.resultDownOF;
		this.resultUpI=vo.resultUpI;
		this.resultUpIF=vo.resultUpIF;
		this.resultUpO=vo.resultUpO;
		this.resultUpOF=vo.resultUpOF;
		this.v1 = vo.v1;
		this.v1F = vo.v1F;
		this.v2 =vo.v2;
		this.v2F = vo.v2F;
	}
	@Override
	public FunctionResult getResult(Date date) {
		FunctionResult result=new FunctionResult();
		result.location.add(ResultType.DOUBLE.getCode());
		result.location.add(ResultType.DOUBLELIST.getCode());
		result.rD=v1+v2;
		result.rLD=new ArrayList<Double>();
		result.rLD.add(v1+v2);
		return result;
	}
	public double getV1() {
		return v1;
	}
	public void setV1(double v1) {
		this.v1 = v1;
	}
	public Function getV1F() {
		return v1F;
	}
	public void setV1F(Function v1f) {
		v1F = v1f;
	}
	public double getV2() {
		return v2;
	}
	public void setV2(double v2) {
		this.v2 = v2;
	}
	public Function getV2F() {
		return v2F;
	}
	public void setV2F(Function v2f) {
		v2F = v2f;
	}
	@Override
	public String toString() {
		return "\nAddFunction [v1=" + v1 + ", v1F=" + v1F + ", v2=" + v2 + ", v2F=" + v2F + ", function=" + function
				+ ", siid=" + siid + ", siidF=" + siidF + ", resultUpI=" + resultUpI + ", resultUpIF=" + resultUpIF
				+ ", resultDownI=" + resultDownI + ", resultDownIF=" + resultDownIF + ", resultUpO=" + resultUpO
				+ ", resultUpOF=" + resultUpOF + ", resultDownO=" + resultDownO + ", resultDownOF=" + resultDownOF
				+ "]\n";
	}

}
