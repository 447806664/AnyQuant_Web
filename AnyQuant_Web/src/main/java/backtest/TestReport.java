package backtest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import data.impl.DataServiceImpl;
import bl.DataHelper;
import tool.MMSTool;

public class TestReport {
	/**回测交易日数量*/
	public int n;
	/**无风险年化收益率*/
	public double risklessReturns;
	/**策略每期股票现金总价值*/
	public List<DateDouble> capital;
	/**基准每期股票现金总价值*/
	public List<DateDouble> bCapital;
	/**策略每期买入总价*/
	public List<Double> inPrice;
	/**策略每期卖出总价*/
	public List<Double> outPrice;
	
	
	/**策略年化收益率（Annualized Returns）*/
	public double annualizedReturns;
	/**基准年化收益率（Benchmark Returns）*/
	public double benchmarkReturns;
	/**阿尔法（Alpha）*/
	public double alpha;
	/**贝塔（Beta）*/
	public double beta;
	/**夏普比率（Sharpe Ratio）*/
	public double sharpeRatio;
	/**收益波动率（Volatility）*/
	public double volatility;
	/**信息比率（Information Ratio）*/
	public double informationRatio;
	/**最大回撤（Max Drawdown）*/
	public double maxDrawdown;
	/**换手率（Turnover Rate）*/
	public double turnoverRate;
	/**策略累计收益率*/
	public List<DateDouble> cumlist;
	/**基准累计收益率*/
	public List<DateDouble> bCumlist;
	
	public TestReport()
	{
		this.n = 0;
		this.risklessReturns = 0;
		this.capital = null;
		this.bCapital = null;
		this.inPrice = null;
		this.outPrice = null;
		this.annualizedReturns = 0;
		this.benchmarkReturns = 0;
		this.alpha = 0;
		this.beta = 0;
		this.sharpeRatio = 0;
		this.volatility = 0;
		this.informationRatio = 0;
		this.maxDrawdown = 0;
		this.turnoverRate = 0;
		this.cumlist = null;
		this.bCumlist = null;
	}
	public TestReport(int n,List<DateDouble> capital,List<DateDouble> bCapital,List<Double> inPrice,List<Double> outPrice) {
		this();
		this.n = n;
		this.risklessReturns = 0.0475;
		this.capital=capital;
		this.bCapital=bCapital;
		this.inPrice=inPrice;
		this.outPrice=outPrice;
		this.cumlist=new ArrayList<DateDouble>();
		this.bCumlist=new ArrayList<DateDouble>();
	}
	public TestReport(int n, double risklessReturns, List<DateDouble> capital,
			List<DateDouble> bCapital, List<Double> inPrice,
			List<Double> outPrice, double annualizedReturns,
			double benchmarkReturns, double alpha, double beta,
			double sharpeRatio, double volatility, double informationRatio,
			double maxDrawdown, double turnoverRate, List<DateDouble> cumlist,
			List<DateDouble> bCumlist) {
		this();
		this.n = n;
		this.risklessReturns = risklessReturns;
		this.capital = capital;
		this.bCapital = bCapital;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.annualizedReturns = annualizedReturns;
		this.benchmarkReturns = benchmarkReturns;
		this.alpha = alpha;
		this.beta = beta;
		this.sharpeRatio = sharpeRatio;
		this.volatility = volatility;
		this.informationRatio = informationRatio;
		this.maxDrawdown = maxDrawdown;
		this.turnoverRate = turnoverRate;
		this.cumlist = cumlist;
		this.bCumlist = bCumlist;
	}
	public void run(Double pEnd,Double pStart,Double mEnd,Double mStart)
	{
		annualizedReturns=DataHelper.controldigit(ar(n,pEnd,pStart));
		benchmarkReturns=DataHelper.controldigit(br(n,mEnd,mStart));
		
		
		List<Double> arlist=new ArrayList<Double>();//策略每期收益
		List<Double> arrlist=new ArrayList<Double>();//策略每期收益率
		List<Double> brlist=new ArrayList<Double>();//基准每期收益
		List<Double> brrlist=new ArrayList<Double>();//基准每期收益
		double cum=0;
		double bCum=0;
		for(int i=0;i<Math.min(capital.size(),bCapital.size())-1;i++)
		{
			arlist.add(capital.get(i+1).value-capital.get(i).value);
			brlist.add(bCapital.get(i+1).value-bCapital.get(i).value);
			arrlist.add(arlist.get(i)/capital.get(i).value);
			brrlist.add(brlist.get(i)/bCapital.get(i).value);
			
			cum+=arrlist.get(i)*100;
			cumlist.add(new DateDouble(capital.get(i).date,DataHelper.controldigit(cum)));
			bCum+=brrlist.get(i)*100;
			bCumlist.add(new DateDouble(capital.get(i).date,DataHelper.controldigit(bCum)));
		}

		beta=beta(MMSTool.cov(arlist, brlist),MMSTool.variance_sample(brlist));
		alpha=alpha(annualizedReturns,benchmarkReturns,risklessReturns,beta);
		volatility=v(MMSTool.variance_sample(arrlist));
		sharpeRatio=sr(annualizedReturns,risklessReturns,volatility);
		List<Double> ablist=new ArrayList<Double>();
		for(int i=0;i<arlist.size();i++)
		{
			ablist.add(arlist.get(i)-brlist.get(i));
		}
		informationRatio=ir(annualizedReturns,benchmarkReturns,Math.sqrt(250*MMSTool.variance_sample(ablist)));
		List<Double> capitalvalue=new ArrayList<Double>();
		for(int i=0;i<capital.size();i++)
		{
			capitalvalue.add(capital.get(i).value);
		}
		maxDrawdown=md(capitalvalue);
		turnoverRate=tr(Math.min(MMSTool.sum_double(inPrice),MMSTool.sum_double(outPrice)),MMSTool.mean(capitalvalue));

		//扩大100倍
		annualizedReturns*=100;
		benchmarkReturns*=100;
		beta*=100;
		alpha*=100;
		volatility*=100;
		sharpeRatio*=100;
		informationRatio*=100;
		maxDrawdown*=100;
		turnoverRate*=100;
		
		annualizedReturns=DataHelper.controldigit(annualizedReturns);
		benchmarkReturns=DataHelper.controldigit(benchmarkReturns);
		beta=DataHelper.controldigit(beta);
		alpha=DataHelper.controldigit(alpha);
		volatility=DataHelper.controldigit(volatility);
		sharpeRatio=DataHelper.controldigit(sharpeRatio);
		informationRatio=DataHelper.controldigit(informationRatio);
		maxDrawdown=DataHelper.controldigit(maxDrawdown);
		turnoverRate=DataHelper.controldigit(turnoverRate);

	}

	/**
	 * 策略年化收益率（Annualized Returns）： 表示投资期限为一年的预期收益率
	 * pEnd:策略最终股票和现金总净值
	 * pStart:策略初始股票和现金总净值
	 * n=回测交易日数量
	 * */
	public double ar(int n,double pEnd, double pStart)
	{
		return Math.pow(pEnd/pStart,250.0/n)-1;
	}
	
	/**
	 * 基准年化收益率（Benchmark Returns）：表示参考标准年化收益率
	 * mEnd=基准最终指数
	 * mStart=基准初始指数
	 * n=回测交易日数量
	 * */
	public double br(int n,double mEnd,double mStart)
	{
		return Math.pow(mEnd/mStart,250.0/n)-1;
	}
	
	/**
	 * 贝塔（Beta）：表示投资的系统性风险，反映了策略对大盘变化的敏感性。
	 * 例如一个策略的Beta为1.3，则大盘涨1%的时候，策略可能涨1.3%，反之亦然；
	 * 如果一个策略的Beta为-1.3，说明大盘涨1%的时候，策略可能跌1.3%，反之亦然。
	 * rP=策略年化收益率
	 * rM=基准年化收益率
	 * sM=基准每日收益的方差
	 * cov(rP,rM)=策略每日收益与基准每日收益的协方差
	 * */
	public double beta(double cov,double sM)
	{
		return cov/sM;
	}
	
	/**
	 * 阿尔法（Alpha）：表示投资中面临的非系统性风险（即Alpha）。
	 * Alpha是投资者获得与市场波动无关的回报，一般用来度量投资者的投资技艺。
	 * 比如投资者获得了12%的回报，其基准获得了10%的回报，那么Alpha或者价值增值的部分就是2%。
	 * rP=策略年化收益率
	 * rM=基准年化收益率
	 * rF=策略无风险收益，即中国固定利率国债年化到期收益-->用4.57的利润率来计算
	 * beta=策略的beta值
	 * Alpha值：
	 * 		α>0	策略相对于风险，获得了超额收益
	 * 		α=0	策略相对于风险，获得了适当收益
	 * 		α<0	策略相对于风险，获得了较少收益
	 * */
	public double alpha(double rP,double rM,double rF,double beta)
	{
		return rP-rF-beta*(rM-rF);
	}

	/**
	 * 收益波动率（Volatility）：用来测量资产的风险性，波动越大代表策略风险越高。
	 * rP=策略每日收益率
	 * rP_m=策略每日平均收益率
	 * n=回测交易日数量
	 * sP=策略每日收益率样本方差
	 */
	public double v(double sP)
	{
		return Math.sqrt(250*sP);
	}
	
	/**
	 * 夏普比率（Sharpe Ratio）：
	 * 表示每承受一单位总风险，会产生多少的超额报酬，可以同时对策略的收益与风险进行综合考虑。
	 * rP=策略年化收益率
	 * rF=策略无风险收益，即中国固定利率国债年化到期收益
	 * dP=策略收益波动率
	 * */
	public double sr(double rP,double rF,double dP)
	{
		return (rP-rF)/dP;
	}
	
	/**
	 * 信息比率（Information Ratio）：衡量单位超额风险带来的超额收益。
	 * 信息比率越大，说明该策略单位跟踪误差所获得的超额收益越高，因此，信息比率较大的策略的表现要优于信息比率较低的基金。
	 * 合理的投资目标应该是在承担适度风险下，尽可能追求高信息比率。
	 * rP=策略年化收益率
	 * rM=基准年化收益率
	 * dT=策略与基准每日收益差值的年化标准差(sqrt(250*日标准差))
	 */
	public double ir(double rP,double rM,double dT)
	{
		return (rP-rM)/dT;
	}
	
	/**
	 * 最大回撤（Max Drawdown）：描述策略可能出现的最糟糕的情况
	 * pX,pY策略某日股票和现金的总价值,y>x
	 * */
	public double md(List<Double> capital)
	{
//		List<Double> ratios=new ArrayList<Double>();
//		for(int i=0;i<capital.size();i++)
//		{
//			for(int j=i+1;j<capital.size();j++)
//			{
//				ratios.add(capital.get(i)-capital.get(j)/capital.get(i));
//			}
//		}
//		return MMSTool.max(ratios);
		
		double ratio=0;
		for(int i=0;i<capital.size();i++)
		{
			for(int j=i+1;j<capital.size();j++)
			{
				double value=capital.get(i)-capital.get(j)/capital.get(i);
				if(ratio<value)
				{
					ratio=value;
				}
			}
		}
		return ratio;
	}
	
	/**
	 * 换手率（Turnover Rate）：描述策略变化的频率以及持有某只股票平均时间的长短
	 * pX=买入总价值与卖出总价值中的较小者
	 * pAvg=虚拟账户平均价值
	 * */
	public double tr(double pX,double pAvg)
	{
		return pX/pAvg;
	}

	@Override
	public String toString() {
		return "TestReport [annualizedReturns=" + annualizedReturns
				+ ", benchmarkReturns=" + benchmarkReturns + ", alpha=" + alpha
				+ ", beta=" + beta + ", sharpeRatio=" + sharpeRatio
				+ ", volatility=" + volatility + ", informationRatio="
				+ informationRatio + ", maxDrawdown=" + maxDrawdown
				+ ", turnoverRate=" + turnoverRate 
				+ "\ncumlist=" + cumlist
				+ "\nbCumlist=" + bCumlist 
				+ "]";
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public double getRisklessReturns() {
		return risklessReturns;
	}
	public void setRisklessReturns(double risklessReturns) {
		this.risklessReturns = risklessReturns;
	}
	public double getAnnualizedReturns() {
		return annualizedReturns;
	}
	public void setAnnualizedReturns(double annualizedReturns) {
		this.annualizedReturns = annualizedReturns;
	}
	public double getBenchmarkReturns() {
		return benchmarkReturns;
	}
	public void setBenchmarkReturns(double benchmarkReturns) {
		this.benchmarkReturns = benchmarkReturns;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getBeta() {
		return beta;
	}
	public void setBeta(double beta) {
		this.beta = beta;
	}
	public double getSharpeRatio() {
		return sharpeRatio;
	}
	public void setSharpeRatio(double sharpeRatio) {
		this.sharpeRatio = sharpeRatio;
	}
	public double getVolatility() {
		return volatility;
	}
	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}
	public double getInformationRatio() {
		return informationRatio;
	}
	public void setInformationRatio(double informationRatio) {
		this.informationRatio = informationRatio;
	}
	public double getMaxDrawdown() {
		return maxDrawdown;
	}
	public void setMaxDrawdown(double maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}
	public double getTurnoverRate() {
		return turnoverRate;
	}
	public void setTurnoverRate(double turnoverRate) {
		this.turnoverRate = turnoverRate;
	}
	public List<DateDouble> getCumlist() {
		return cumlist;
	}
	public void setCumlist(List<DateDouble> cumlist) {
		this.cumlist = cumlist;
	}
	public List<DateDouble> getbCumlist() {
		return bCumlist;
	}
	public void setbCumlist(List<DateDouble> bCumlist) {
		this.bCumlist = bCumlist;
	}
}
