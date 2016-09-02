package tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.set.SynchronizedSortedSet;

import backtest.DateDouble;
import backtest.TestReport;
import vo.Flag;
import vo.RealTestVO;
import function.Function;
import function.FunctionResult;
import function.choose.AreaFunction;
import function.choose.AreaVO;
import function.choose.AttributeFunction;
import function.choose.AttributeVO;
import function.choose.ChooseStock;
import function.choose.ConceptFunction;
import function.choose.ConceptVO;
import function.choose.IndustryFunction;
import function.choose.IndustryVO;
import function.choose.IntersectionFunction;
import function.choose.IntersectionVO;
import function.choose.PairFunction;
import function.choose.PairVO;
import function.flag.CrossFunction;
import function.flag.CrossPointFunction;
import function.flag.CrossVO;
import function.flag.DataFunction;
import function.flag.DataVO;
import function.flag.DownTrendFunction;
import function.flag.MaxMinFunction;
import function.flag.MaxMinVO;
import function.flag.TrendFunction;
import function.flag.TrendVO;
import function.flag.UpTrendFunction;
import function.order.ShareFunction;
import function.order.SharePercentFunction;
import function.order.ShareTargetFunction;
import function.order.ValueFunction;
import function.order.ValuePercentFunction;
import function.order.ValueTargetFunction;
import function.risk.StandardPercentFunction;
import function.risk.StandardPercentVO;
import function.tool.AddFunction;
import function.tool.DivideFunction;
import function.tool.MeanFunction;
import function.tool.MinusFunction;
import function.tool.MultipleFunction;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class JsonExchangeTool {
	public static List<ChooseStock> getStock(String json)
	{
		List<ChooseStock> list=new ArrayList<ChooseStock>();
		JSONArray jArray=null;
		JSONObject jObject=null;
		jArray=JSONArray.fromObject(json);
		for(int i=0;i<jArray.size();i++)
		{
			jObject=jArray.getJSONObject(i);
			String siid=jObject.getString("siid");
			Double percent=jObject.getDouble("percent");
			list.add(new ChooseStock(siid,percent));
		}
		return list;
	}
	public static List<String> getSaveStocks(String json)
	{
		List<String> list=new ArrayList<String>();
		JSONArray jArray=JSONArray.fromObject(json);
		for(int i=0;i<jArray.size();i++)
		{
			list.add(jArray.getString(i));
		}
		return list;
	}
	public static List<Flag> getFlag(String json)
	{
		List<Flag> flags=new ArrayList<Flag>();
		JSONArray jArray=JSONArray.fromObject(json);
		for(int i=0;i<jArray.size();i++)
		{
			JSONObject jObject=(JSONObject) jArray.get(i);
			flags.add(new Flag(getOrder(jObject.get("orderType").toString()),getFunctionList(jObject.get("flagList").toString())));
		}
		return flags;
	}
	public static FunctionResult getResult(String json)
	{
		FunctionResult result=new FunctionResult();
		JSONObject jObject=JSONObject.fromObject(json);
		try{
			result.location=jObject.getJSONArray("location");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		if(result.location.size()==0)
		{
			return null;
		}
		try{
			result.rB=jObject.getBoolean("rB");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rI=jObject.getInt("rI");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rD=jObject.getDouble("rD");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rS=jObject.getString("rS");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rL=jObject.getLong("rL");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rLI=jObject.getJSONArray("rLI");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rLD=jObject.getJSONArray("rLD");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rLS=jObject.getJSONArray("rLS");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		try{
			result.rLL=jObject.getJSONArray("rLL");
		} catch(Exception e)
		{
//			e.printStackTrace();
		}
		return result;
	}
	
	public static Function getFunction(String json)
	{
		JSONObject jObject=JSONObject.fromObject(json);
		if(!jObject.toString().equals("null")){
			String function=jObject.getString("function");
			String siid=null;
			JSONObject siidFJ=null;
			JSONObject resultUpIJ=null;
			JSONObject resultUpIFJ=null;
			JSONObject resultDownIJ=null;
			JSONObject resultDownIFJ=null;
			JSONObject resultUpOJ=null;
			JSONObject resultUpOFJ=null;
			JSONObject resultDownOJ=null;
			JSONObject resultDownOFJ=null;
			
			Function siidF=null;
			FunctionResult resultUpI=null;
			Function resultUpIF=null;
			FunctionResult resultDownI=null;
			Function resultDownIF=null;
			FunctionResult resultUpO=null;
			Function resultUpOF=null;
			FunctionResult resultDownO=null;
			Function resultDownOF=null;
			
			try{
				resultUpOJ=jObject.getJSONObject("resultUpO");
				resultUpO=getResult(resultUpOJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultDownOJ=jObject.getJSONObject("resultDownO");
				resultDownO=getResult(resultDownOJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultUpIJ=jObject.getJSONObject("resultUpI");
				resultUpI=getResult(resultUpIJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultDownIJ=jObject.getJSONObject("resultDownI");
				resultDownI=getResult(resultDownIJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultUpOFJ=jObject.getJSONObject("resultUpOF");
				resultUpOF=getFunction(resultUpOFJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultDownOFJ=jObject.getJSONObject("resultDownOF");
				resultDownOF=getFunction(resultDownOFJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultUpIFJ=jObject.getJSONObject("resultUpIF");
				resultUpIF=getFunction(resultUpIFJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			try{
				resultDownIFJ=jObject.getJSONObject("resultDownIF");
				resultDownIF=getFunction(resultDownIFJ.toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			
			String attribute=null;
			String siid1=null;
			String siid2=null;
			String attribute1=null;
			String attribute2=null;
			int num=0;
			int day=0;
			String area=null;
			String concept=null;
			String industry=null;
			double up=0;
			double down=0;
			List<String> stockList1=null;
			List<String> stockList2=null;
			int sign=0;
			String standardAttr=null;
			double percent=0;
			double standard=0;
			double v1=0;
			double v2=0;
			List<Double> valueList=null;
			int mm=0;
			int loc=0;
			
			JSONArray stockList1J=null;
			JSONArray stockList2J=null;
			
			Function attributeF=null;
			Function siid1F=null;
			Function siid2F=null;
			Function attribute1F=null;
			Function attribute2F=null;
			Function numF=null;
			Function dayF=null;
			Function areaF=null;
			Function conceptF=null;
			Function industryF=null;
			Function upF=null;
			Function downF=null;
			Function stockList1F=null;
			Function stockList2F=null;
			Function signF=null;
			Function standardF=null;
			Function percentF=null;
			Function v1F=null;
			Function v2F=null;
			Function valueListF=null;
			Function mmF=null;
			Function locF=null;
			
			JSONObject attributeFJ=null;
			JSONObject siid1FJ=null;
			JSONObject siid2FJ=null;
			JSONObject attribute1FJ=null;
			JSONObject attribute2FJ=null;
			JSONObject numFJ=null;
			JSONObject dayFJ=null;
			JSONObject areaFJ=null;
			JSONObject conceptFJ=null;
			JSONObject industryFJ=null;
			JSONObject upFJ=null;
			JSONObject downFJ=null;
			JSONObject stockList1FJ=null;
			JSONObject stockList2FJ=null;
			JSONObject signFJ=null;
			JSONObject standardFJ=null;
			JSONObject percentFJ=null;
			JSONObject v1FJ=null;
			JSONObject v2FJ=null;
			JSONObject valueListFJ=null;
			JSONObject mmFJ=null;
			JSONObject locFJ=null;
			
			switch(function)
			{
			//choose
			case "Pair":
				siid = (String) jObject.get("siid");
				siidFJ = jObject.getJSONObject("siidF");
				siidF = getFunction(siidFJ.toString());
				num = jObject.getInt("num");
				numFJ = jObject.getJSONObject("numF");
				numF =getFunction(numFJ.toString());
				return new PairFunction(new PairVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid, siidF, num, numF));
			case "Area":
				area=jObject.getString("area");
				areaFJ=jObject.getJSONObject("areaF");
				areaF=getFunction(areaFJ.toString());
				return new AreaFunction(new AreaVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, area, areaF));
			case "Concept":
				concept=jObject.getString("concept");
				conceptFJ=jObject.getJSONObject("conceptF");
				conceptF=getFunction(conceptFJ.toString());
				return new ConceptFunction(new ConceptVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, concept, conceptF));
			case "Industry":
				industry=jObject.getString("industry");
				industryFJ=jObject.getJSONObject("industryFJ");
				industryF=getFunction(industryFJ.toString());
				return new IndustryFunction(new IndustryVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, industry, industryF));
			case "Intersection":
				try{
					stockList1 = jObject.getJSONArray("stockList1");
				} catch(Exception e)
				{
					e.printStackTrace();
				}
				stockList1FJ = jObject.getJSONObject("stockList1F");
				stockList1F = getFunction(stockList1FJ.toString());
				try{
					stockList2 = jObject.getJSONArray("stockList2");
				} catch(Exception e)
				{
					e.printStackTrace();
				}
				stockList2FJ = jObject.getJSONObject("stockList2F");
				stockList2F = getFunction(stockList2FJ.toString());
				return new IntersectionFunction(new IntersectionVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, stockList1J, stockList1F, stockList2J, stockList2F));
			case "Attribute":
				up = jObject.getDouble("up");
				upFJ = jObject.getJSONObject("upF");
				upF = getFunction(upFJ.toString());
				down = jObject.getDouble("down");
				downFJ = jObject.getJSONObject("downF");
				downF = getFunction(upFJ.toString());
				attribute = jObject.getString("attribute");
				attributeFJ = jObject.getJSONObject("attributeF");
				attributeF = getFunction(attributeFJ.toString());
				return new AttributeFunction(new AttributeVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, up, upF, down, downF, attribute2, attributeF));
				
			//risk	
			case "StandardPercent":
				sign=jObject.getInt("sign");
				signFJ=jObject.getJSONObject("signF");
				signF=getFunction(signFJ.toString());
				siid=jObject.getString("siid");
				siidFJ=jObject.getJSONObject("siidF");
				siidF=getFunction(siidFJ.toString());
				attribute=jObject.getString("attribute");
				attributeFJ=jObject.getJSONObject("attributeF");
				attributeF=getFunction(attributeFJ.toString());
				standardAttr=jObject.getString("standard");
				standardFJ=jObject.getJSONObject("standardF");
				standardF=getFunction(standardFJ.toString());
				percent=jObject.getDouble("percent");
				percentFJ=jObject.getJSONObject("percentF");
				percentF=getFunction(percentFJ.toString());
				return new StandardPercentFunction(new StandardPercentVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, sign, signF, siid, siidF, attribute, attributeF, standardAttr, standardF, percent, percentF));
				
			//flag	
			case "Trend":
				siid = (String) jObject.get("siid");
				siidFJ = jObject.getJSONObject("siidF");
				siidF = getFunction(siidFJ.toString());
				attribute = (String) jObject.get("attribute");
				attributeFJ = jObject.getJSONObject("attributeF");
				attributeF = getFunction(attributeFJ.toString());
				day = jObject.getInt("day");
				dayFJ = jObject.getJSONObject("dayF");
				dayF = getFunction(dayFJ.toString());
				standard = jObject.getDouble("standard");
				standardFJ = jObject.getJSONObject("standardF");
				standardF = getFunction(standardFJ.toString());
				return new TrendFunction(new TrendVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid, siidF, attribute, attributeF, day, dayF, standard, standardF));
			case "UpTrend":
				siid = (String) jObject.get("siid");
				siidFJ = jObject.getJSONObject("siidF");
				siidF = getFunction(siidFJ.toString());
				attribute = (String) jObject.get("attribute");
				attributeFJ = jObject.getJSONObject("attributeF");
				attributeF = getFunction(attributeFJ.toString());
				day = jObject.getInt("day");
				dayFJ = jObject.getJSONObject("dayF");
				dayF = getFunction(dayFJ.toString());
				standard = jObject.getDouble("standard");
				standardFJ = jObject.getJSONObject("standardF");
				standardF = getFunction(standardFJ.toString());
				return new UpTrendFunction(new TrendVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid, siidF, attribute, attributeF, day, dayF, standard, standardF));
			case "DownTrend":
				siid = (String) jObject.get("siid");
				siidFJ = jObject.getJSONObject("siidF");
				siidF = getFunction(siidFJ.toString());
				attribute = (String) jObject.get("attribute");
				attributeFJ = jObject.getJSONObject("attributeF");
				attributeF = getFunction(attributeFJ.toString());
				day = jObject.getInt("day");
				dayFJ = jObject.getJSONObject("dayF");
				dayF = getFunction(dayFJ.toString());
				standard = jObject.getDouble("standard");
				standardFJ = jObject.getJSONObject("standardF");
				standardF = getFunction(standardFJ.toString());
				return new DownTrendFunction(new TrendVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid, siidF, attribute, attributeF, day, dayF, standard, standardF));
			case "Cross":
				siid1 = (String) jObject.get("siid1");
				siid1FJ = jObject.getJSONObject("siid1F");
				siid1F = getFunction(siid1FJ.toString());
				siid2 = (String) jObject.get("siid2");
				siid2FJ = jObject.getJSONObject("siid2F");
				siid2F = getFunction(siid2FJ.toString());
				attribute1 = (String) jObject.get("attribute1");
				attribute1FJ = jObject.getJSONObject("attribute1F");
				attribute1F = getFunction(attribute1FJ.toString());
				attribute2 = (String) jObject.get("attribute2");
				attribute2FJ = jObject.getJSONObject("attribute2F");
				attribute2F = getFunction(attribute2FJ.toString());
				day = jObject.getInt("day");
				dayFJ = jObject.getJSONObject("dayF");
				dayF = getFunction(dayFJ.toString());
				return new CrossFunction(new CrossVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid1, siid1F, attribute1, attribute1F, siid2, siid2F, attribute2, attribute2F, day, dayF));
			case "CrossPoint":
				siid1 = (String) jObject.get("siid1");
				siid1FJ = jObject.getJSONObject("siid1F");
				siid1F = getFunction(siid1FJ.toString());
				siid2 = (String) jObject.get("siid2");
				siid2FJ = jObject.getJSONObject("siid2F");
				siid2F = getFunction(siid2FJ.toString());
				attribute1 = (String) jObject.get("attribute1");
				attribute1FJ = jObject.getJSONObject("attribute1F");
				attribute1F = getFunction(attribute1FJ.toString());
				attribute2 = (String) jObject.get("attribute2");
				attribute2FJ = jObject.getJSONObject("attribute2F");
				attribute2F = getFunction(attribute2FJ.toString());
				day = jObject.getInt("day");
				dayFJ = jObject.getJSONObject("dayF");
				dayF = getFunction(dayFJ.toString());
				return new CrossPointFunction(new CrossVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid1, siid1F, attribute1, attribute1F, siid2, siid2F, attribute2, attribute2F, day, dayF));
			case "MaxMin":
				siid=jObject.getString("siid");	
				siidFJ=jObject.getJSONObject("siidF");
				siidF=getFunction(siidFJ.toString());
				attribute=jObject.getString("attribute");
				attributeFJ=jObject.getJSONObject("attributeF");
				attributeF=getFunction(attributeFJ.toString());
				mm=jObject.getInt("mm");
				mmFJ=jObject.getJSONObject("mmF");
				mmF=getFunction(mmFJ.toString());
				num=jObject.getInt("num");
				numFJ=jObject.getJSONObject("numF");
				numF=getFunction(numFJ.toString());
				loc=jObject.getInt("loc");
				locFJ=jObject.getJSONObject("locF");
				locF=getFunction(locFJ.toString());
				return new MaxMinFunction(new MaxMinVO(siid, siidF, resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, attribute, attributeF, mm, mmF, num, numF, loc, locF));
			case "Data":
				siid=jObject.getString("siid");
				siidFJ=jObject.getJSONObject("siidF");
				siidF=getFunction(siidFJ.toString());
				attribute=jObject.getString("attribute");
				attributeFJ=jObject.getJSONObject("attributeF");
				attributeF=getFunction(attributeFJ.toString());
				day=jObject.getInt("day");
				dayFJ=jObject.getJSONObject("dayF");
				dayF=getFunction(dayFJ.toString());
				return new DataFunction(new DataVO(resultUpI, resultUpIF, resultDownI, resultDownIF, resultUpO, resultUpOF, resultDownO, resultDownOF, siid, siidF, attribute, attributeF, day, dayF));

			//tool
			case "Add":
				v1=jObject.getDouble("v1");
				v1FJ=jObject.getJSONObject("v1F");
				v1F=getFunction(v1FJ.toString());
				v2=jObject.getDouble("v2");
				v2FJ=jObject.getJSONObject("v2F");
				v2F=getFunction(v2FJ.toString());
				return new AddFunction(v1, v1F, v2, v2F);
			case "Minus":
				v1=jObject.getDouble("v1");
				v1FJ=jObject.getJSONObject("v1F");
				v1F=getFunction(v1FJ.toString());
				v2=jObject.getDouble("v2");
				v2FJ=jObject.getJSONObject("v2F");
				v2F=getFunction(v2FJ.toString());
				return new MinusFunction(v1, v1F, v2, v2F);
			case "Multiple":
				v1=jObject.getDouble("v1");
				v1FJ=jObject.getJSONObject("v1F");
				v1F=getFunction(v1FJ.toString());
				v2=jObject.getDouble("v2");
				v2FJ=jObject.getJSONObject("v2F");
				v2F=getFunction(v2FJ.toString());
				return new MultipleFunction(v1, v1F, v2, v2F);
			case "Divide":
				v1=jObject.getDouble("v1");
				v1FJ=jObject.getJSONObject("v1F");
				v1F=getFunction(v1FJ.toString());
				v2=jObject.getDouble("v2");
				v2FJ=jObject.getJSONObject("v2F");
				v2F=getFunction(v2FJ.toString());
				return new DivideFunction(v1, v1F, v2, v2F);
			case "Mean":
				try{
					valueList=jObject.getJSONArray("valueList");
				} catch(Exception e)
				{
					e.printStackTrace();
				}
				valueListFJ=jObject.getJSONObject("valueListF");
				valueListF=getFunction(valueListFJ.toString());
				return new MeanFunction(valueList, valueListF);
			default:
				return null;
			}
		}
		return null;
	}
	
	public static List<List<Function>> getFunctionList(String json)
	{
		List<List<Function>> list=new ArrayList<List<Function>>();
		JSONArray jArrayOut=null;
		try{
			jArrayOut=JSONArray.fromObject(json);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println("jArrayOut"+jArrayOut);
		if(jArrayOut.toString().equals("null")||jArrayOut.get(0).equals(null))
		{
			return list;
		}
		for(int j=0;j<jArrayOut.size();j++)
		{
			list.add(new ArrayList<Function>());
			JSONArray jArrayIn=null;
			try{
				jArrayIn=jArrayOut.getJSONArray(j);
//				System.out.println("jArrayIn"+jArrayIn);
				for(int i=0;i<jArrayIn.size();i++)
				{
					list.get(j).add(getFunction(jArrayIn.getJSONObject(i).toString()));
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}
	//不处理result
	public static Function getOrder(String json)
	{
		JSONObject jObject=JSONObject.fromObject(json);
		String function=(String)jObject.get("function");
		switch(function)
		{
		case "Share":
			ShareFunction share=new ShareFunction();
			share.setFunction(function);
			share.setShare(jObject.getInt("share"));
			share.setShareF(getFunction(jObject.getJSONObject("shareF").toString()));
			share.setSiid(jObject.getString("siid"));
			share.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return share;
		case "SharePercent":
			SharePercentFunction sharePercent=new SharePercentFunction();
			sharePercent.setFunction(function);
			sharePercent.setPercent(jObject.getInt("percent"));
			sharePercent.setPercentF(getFunction(jObject.getJSONObject("percentF").toString()));
			sharePercent.setSiid(jObject.getString("siid"));
			sharePercent.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return sharePercent;
		case "ShareTarget":
			ShareTargetFunction shareTarget=new ShareTargetFunction();
			shareTarget.setFunction(function);
			shareTarget.setShare(jObject.getInt("share"));
			shareTarget.setShareF(getFunction(jObject.getJSONObject("shareF").toString()));
			shareTarget.setSiid(jObject.getString("siid"));
			shareTarget.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return shareTarget;
		case "Value":
			ValueFunction value=new ValueFunction();
			value.setFunction(function);
			value.setValue(jObject.getInt("value"));
			value.setValueF(getFunction(jObject.getJSONObject("valueF").toString()));
			value.setSiid(jObject.getString("siid"));
			value.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return value;
		case "ValuePercent":
			ValuePercentFunction valuePercent=new ValuePercentFunction();
			valuePercent.setFunction(function);
			valuePercent.setPercent(jObject.getInt("percent"));
			valuePercent.setPercentF(getFunction(jObject.getJSONObject("percentF").toString()));
			valuePercent.setSiid(jObject.getString("siid"));
			valuePercent.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return valuePercent;
		case "ValueTarget":
			ValueTargetFunction valueTarget=new ValueTargetFunction();
			valueTarget.setFunction(function);
			valueTarget.setValue(jObject.getInt("value"));
			valueTarget.setValueF(getFunction(jObject.getJSONObject("valueF").toString()));
			valueTarget.setSiid(jObject.getString("siid"));
			valueTarget.setSiidF(getFunction(jObject.getJSONObject("siidF").toString()));
			return valueTarget;
		}
		return null;
	}
	public static RealTestVO getRealTest(String json)
	{
		RealTestVO vo=new RealTestVO();
		try
		{
			JSONObject jObject=JSONObject.fromObject(json);
			vo.setCash(jObject.getDouble("cash"));
			vo.setN(jObject.getInt("n"));
//			vo.orderType=getOrder(jObject.get("orderType").toString());
//			vo.stockList=getStock(jObject.get("stockList").toString());
			List<DateDouble> capital=new ArrayList<DateDouble>();
			JSONArray jCapital=(JSONArray)jObject.get("capital");
			for(int i=0;i<jCapital.size();i++)
			{
				JSONObject jDateDouble=(JSONObject) jCapital.get(i);
				capital.add(new DateDouble(jDateDouble.getLong("date"),jDateDouble.getDouble("value")));
			}
			vo.setCapital(capital);
//			List<List<Function>> flagList=getFunction(jObject.get("flagList").toString());
//			vo.flagList=flagList;
			List<Integer> numList=new ArrayList<Integer>();
			JSONArray jNum=(JSONArray) jObject.get("numList");
			for(int i=0;i<jNum.size();i++)
			{
				numList.add((int)jNum.get(i));
			}
			vo.setNumList(numList);
			JSONArray jHistory=(JSONArray) jObject.get("history");
			List<String> history=new ArrayList<String>();
			for(int i=0;i<jHistory.size();i++)
			{
				history.add(jHistory.getString(i));
			}
			vo.setHistory(history);
		} catch(JSONException e)
		{
			return new RealTestVO();
		}
		return vo;
	}
	public static TestReport getReport(String report) {
		JSONObject jObject=JSONObject.fromObject(report);
		if(!jObject.toString().equals("null"))
		{
			int n=jObject.getInt("n");
			double risklessReturns=jObject.getDouble("risklessReturns");
			double annualizedReturns=jObject.getDouble("annualizedReturns");
			double benchmarkReturns=jObject.getDouble("benchmarkReturns");
			double alpha=jObject.getDouble("alpha");
			double beta=jObject.getDouble("beta");
			double sharpeRatio=jObject.getDouble("sharpeRatio");
			double volatility=jObject.getDouble("volatility");
			double informationRatio=jObject.getDouble("informationRatio");
			double maxDrawdown=jObject.getDouble("maxDrawdown");
			double turnoverRate=jObject.getDouble("turnoverRate");
			List<DateDouble> cumlist=new ArrayList<DateDouble>();
			List<DateDouble> bCumlist=new ArrayList<DateDouble>();
			List<DateDouble> capital=new ArrayList<DateDouble>();
			List<DateDouble> bCapital=new ArrayList<DateDouble>();
			List<Double> inPrice=new ArrayList<Double>();
			List<Double> outPrice=new ArrayList<Double>();
			JSONArray jCumlist=jObject.getJSONArray("cumlist");
			JSONArray jBCumlist=jObject.getJSONArray("bCumlist");
			JSONArray jCapital=jObject.getJSONArray("capital");
			JSONArray jBCapital=jObject.getJSONArray("bCapital");
			JSONArray jInPrice=jObject.getJSONArray("inPrice");
			JSONArray jOutPrice=jObject.getJSONArray("outPrice");
			for(int i=0;i<jCumlist.size();i++)
			{
				JSONObject jobject=jCumlist.getJSONObject(i);
				cumlist.add(new DateDouble(jobject.getLong("date"),jobject.getDouble("value")));
			}
			for(int i=0;i<jBCumlist.size();i++)
			{
				JSONObject jobject=jBCumlist.getJSONObject(i);
				bCumlist.add(new DateDouble(jobject.getLong("date"),jobject.getDouble("value")));
			}
			for(int i=0;i<jCapital.size();i++)
			{
				JSONObject jobject=jCapital.getJSONObject(i);
				capital.add(new DateDouble(jobject.getLong("date"),jobject.getDouble("value")));
			}
			for(int i=0;i<jBCapital.size();i++)
			{
				JSONObject jobject=jBCapital.getJSONObject(i);
				bCapital.add(new DateDouble(jobject.getLong("date"),jobject.getDouble("value")));
			}
			for(int i=0;i<jInPrice.size();i++)
			{
				inPrice.add(jInPrice.getDouble(i));
			}
			for(int i=0;i<jOutPrice.size();i++)
			{
				outPrice.add(jOutPrice.getDouble(i));
			}
			TestReport testReport=new TestReport(n, risklessReturns, capital, bCapital, inPrice, outPrice, annualizedReturns, benchmarkReturns, alpha, beta, sharpeRatio, volatility, informationRatio, maxDrawdown, turnoverRate, cumlist, bCumlist);
			return testReport;
		}
		return null;
	}
}
