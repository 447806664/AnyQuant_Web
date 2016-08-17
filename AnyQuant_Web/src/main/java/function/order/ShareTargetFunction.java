package function.order;

import function.Function;
import function.FunctionResult;
import function.ResultType;
/**指定仓位股数订单*/
public class ShareTargetFunction extends Function{
	public String siid;
	public int share;
	public double price;
	
	public ShareTargetFunction(){
		this.function="ShareTarget";
	}
	public ShareTargetFunction(ShareVO vo)
	{
		this.function="ShareTarget";
		this.siid=vo.siid;
		this.share=vo.share;
		this.price=vo.price;
	}
	
	public ShareTargetFunction(String siid, int share, double price) {
		super();
		this.siid = siid;
		this.share = share;
		this.price = price;
	}
	@Override
	public FunctionResult getResult() {
		FunctionResult result=new FunctionResult();
		result.location.add(ResultType.STRING);
		result.location.add(ResultType.DOUBLELIST);
		result.rD=share;
		result.rS=siid;
		return result;
	}
	
	public String getSiid() {
		return siid;
	}
	public void setSiid(String siid) {
		this.siid = siid;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ShareTargetFunction [siid=" + siid + ", share=" + share
				+ ", price=" + price + "]";
	}
}
