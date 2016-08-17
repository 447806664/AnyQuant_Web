package service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import function.Function;
import function.choose.ChooseStock;
import po.FunctionDisRnk;
import po.Strategy;
import po.StrategySearch;
import mapper.FunctionMapper;
import mapper.StrategyMapper;
import service.StrategyService;
import tool.JsonExchangeTool;
import vo.StrategyVO;

public class StrategyServiceImpl implements StrategyService {
	@Autowired
	public StrategyMapper strategyMapper;
	@Autowired
	public FunctionMapper functionMapper;
	
	public StrategyServiceImpl(){};
	public StrategyServiceImpl(StrategyMapper strategyMapper,FunctionMapper functionMapper) {
		super();
		this.strategyMapper = strategyMapper;
		this.functionMapper = functionMapper;
	}
	@Override
	public void makeStrategy(StrategyVO vo) {
		Strategy strategy=new Strategy(vo);
		
		try {
			strategyMapper.insert(strategy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public StrategyVO getSingleStrategy(String userName, String createrName,
			String strategyName) {
		StrategySearch search=new StrategySearch(userName,createrName,strategyName);
		Strategy strategy=null;
		try {
			strategy=strategyMapper.selectStrategy(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new StrategyVO(strategy);
	}
	@Override
	public List<StrategyVO> getSelfStrategy(String userName) {
		List<Strategy> strategyList=null;
		StrategySearch search=new StrategySearch();
		search.setUserName(userName);
		try {
			strategyList=strategyMapper.selectSelf(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<StrategyVO> voList=new ArrayList<StrategyVO>();
		for(int i=0;i<strategyList.size();i++)
		{
			voList.add(new StrategyVO(strategyList.get(i)));
		}
		return voList;
	}
	@Override
	public List<StrategyVO> getSaveStrategy(String userName) {
		List<Strategy> strategyList=null;
		StrategySearch search=new StrategySearch();
		search.setUserName(userName);
		try {
			strategyList=strategyMapper.selectSave(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<StrategyVO> voList=new ArrayList<StrategyVO>();
		for(int i=0;i<strategyList.size();i++)
		{
			voList.add(new StrategyVO(strategyList.get(i)));
		}
		return voList;
	}
	@Override
	public List<FunctionDisRnk> getRankFunctions(int rank) {
		List<FunctionDisRnk> list=null;
		try {
			list=functionMapper.selectRank(rank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public FunctionDisRnk getFunctionDisRnk(String name) {
		FunctionDisRnk fdr=null;
		try {
			fdr=functionMapper.selectFunction(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdr;
	}
	@Override
	public List<FunctionDisRnk> getTypeFunction(String type) {
		List<FunctionDisRnk> fdrList=null;
		try {
			fdrList=functionMapper.selectType(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdrList;
	}
	@Override
	public List<FunctionDisRnk> getTRFunction(FunctionDisRnk fdr) {
		List<FunctionDisRnk> fdrList=null;
		try {
			fdrList=functionMapper.selectTypeRank(fdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdrList;
	}

}
