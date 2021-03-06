package function.choose;

import java.sql.Date;

import tool.ListTool;
import function.Function;
import function.FunctionResult;
import function.ResultType;
/**配对选股方法*/
public class PairFunction extends Function{
	/**配对股票数（1或2或3）*/
	public int num;
	public Function numF;
	
	public PairFunction()
	{
		super();
		this.function="Pair";
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
		this.num=1;
		this.numF=null;
	}
	public PairFunction(int num) {
		this();
		this.function="Pair";
		this.num = num;
	}
	public PairFunction(int num, Function numF) {
		this();
		this.function="Pair";
		this.num = num;
		this.numF = numF;
	}
	public PairFunction(PairVO vo)
	{
		this();
		this.function="Pair";
		this.resultUpI = vo.resultUpI;
		this.resultUpIF = vo.resultUpIF;
		this.resultDownI = vo.resultDownI;
		this.resultDownIF = vo.resultDownIF;
		this.resultUpO = vo.resultUpO;
		this.resultUpOF = vo.resultUpOF;
		this.resultDownO = vo.resultDownO;
		this.resultDownOF = vo.resultDownOF;
		
		this.siid=vo.siid;
		this.siidF=vo.siidF;
		this.num=vo.num;
		this.numF=vo.numF;
	}
	
	@Override
	public FunctionResult getResult(Date date) {
		if(numF!=null)
		{
			num=numF.getResult(date).rI;
		}
		FunctionResult result=new FunctionResult();
		result.location.add(ResultType.STRINGLIST.getCode());
		result.rLS=new ListTool().getPair(siid, num);
		return result;
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
		return "PairFunction [num=" + num + ", numF=" + numF + ", function=" + function + ", siid=" + siid + ", siidF="
				+ siidF + ", resultUpI=" + resultUpI + ", resultUpIF=" + resultUpIF + ", resultDownI=" + resultDownI
				+ ", resultDownIF=" + resultDownIF + ", resultUpO=" + resultUpO + ", resultUpOF=" + resultUpOF
				+ ", resultDownO=" + resultDownO + ", resultDownOF=" + resultDownOF + "]";
	}
	
}
