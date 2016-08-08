package mapper;

import po.UserInf;

public interface UserInfMapper {
	/**用户注册*/
	public void signUp(UserInf userInf) throws Exception;
	/**用户查询*/
	public UserInf select(String userName) throws Exception;
}