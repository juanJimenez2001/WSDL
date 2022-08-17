/**
 * UPMGeoCachingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package es.upm.fi.sos;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordBackEnd;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePasswordResponseE;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.ExistUser;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.LoginBackEnd;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserE;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUserResponseE;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.UserBackEnd;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Username;
import es.upm.fi.sos.model.xsd.FollowerList;
import es.upm.fi.sos.model.xsd.Response;
import es.upm.fi.sos.model.xsd.Treasure;
import es.upm.fi.sos.model.xsd.TreasureList;
import es.upm.fi.sos.model.xsd.User;

import org.apache.axis2.AxisFault;
/**
 *  UPMGeoCachingSkeleton java skeleton for the axisService
 */
public class UPMGeoCachingSkeleton{

	private static HashMap<String,ArrayList<String>> followers = new HashMap<>();
	private static HashMap<String,ArrayList<Treasure>> treasuresCreated = new HashMap<>();
	private static HashMap<String,ArrayList<Treasure>> treasuresFound = new HashMap<>();
	private static HashMap<String, String> activeUsers = new HashMap<>();
	private User sessionUser = null;
	private String adminUsername="admin";
	private String adminPwd="admin";
	private UPMAuthenticationAuthorizationWSSkeletonStub stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
	private int sesionesActivas=0;


	public UPMGeoCachingSkeleton() throws AxisFault{
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param logout 
	 * @return  
	 * @throws AxisFault 
	 */
	public void logout(es.upm.fi.sos.Logout logout){
		if(sessionUser!=null){
			sesionesActivas--;
			System.out.println("Logout correcto, " + sessionUser.getName() +" tiene "+ sesionesActivas + " sesiones activas");
			if(sesionesActivas == 0){
				sessionUser = null;
			}

		}else System.out.println("Logout incorrecto, no hay ninguna sesión abierta");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param removeFollower 
	 * @return removeFollowerResponse 
	 */

	public es.upm.fi.sos.RemoveFollowerResponse removeFollower(es.upm.fi.sos.RemoveFollower removeFollower){
		RemoveFollowerResponse response = new RemoveFollowerResponse();
		Response responseParam = new Response();
		String followerUsername = removeFollower.getArgs0().getUsername();
		Username usernameParam = new Username();
		usernameParam.setName(followerUsername);
		boolean result = false;

		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName()) && activeUsers.containsKey(followerUsername)){
			String usernameSession = sessionUser.getName();
			if(followers.get(usernameSession).contains(followerUsername)){
				followers.get(usernameSession).remove(followerUsername);
				result = true;
			}
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyTreasuresFound 
	 * @return getMyTreasuresFoundResponse 
	 */

	public es.upm.fi.sos.GetMyTreasuresFoundResponse getMyTreasuresFound(es.upm.fi.sos.GetMyTreasuresFound getMyTreasuresFound){
		GetMyTreasuresFoundResponse response = new GetMyTreasuresFoundResponse();
		TreasureList treasureList = new TreasureList();
		boolean result = false;
		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName())){
			List<Treasure> tesoros = treasuresFound.get(sessionUser.getName());
			String [] treasureNames = new String[tesoros.size()];
			double [] treasureAlts = new double[tesoros.size()];
			double [] treasureLats = new double[tesoros.size()];	
			if(!tesoros.isEmpty()){
				for(int j=tesoros.size()-1, i=0; j>=0 ; j--,i++){
					treasureNames[i] = tesoros.get(j).getName();
					treasureAlts[i] = tesoros.get(j).getAltitude();
					treasureLats[i] = tesoros.get(j).getLatitude();
				}
			}
			treasureList.setNames(treasureNames);
			treasureList.setAlts(treasureAlts);
			treasureList.setLats(treasureLats);
			result = true;
		}

		treasureList.setResult(result);
		response.set_return(treasureList);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyFollowers 
	 * @return getMyFollowersResponse 
	 */

	public es.upm.fi.sos.GetMyFollowersResponse getMyFollowers(es.upm.fi.sos.GetMyFollowers getMyFollowers){
		GetMyFollowersResponse response = new GetMyFollowersResponse();
		FollowerList followerList = new FollowerList();	
		boolean result = false;
		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName())){
			String usernameSession = sessionUser.getName();	
			String[] followerArr = followers.get(usernameSession).toArray(new String[0]);
			followerList.setFollowers(followerArr);
			result = true;
		}
		followerList.setResult(result);
		response.set_return(followerList);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyTreasuresCreated 
	 * @return getMyTreasuresCreatedResponse 
	 */

	public es.upm.fi.sos.GetMyTreasuresCreatedResponse getMyTreasuresCreated(es.upm.fi.sos.GetMyTreasuresCreated getMyTreasuresCreated){
		GetMyTreasuresCreatedResponse response = new GetMyTreasuresCreatedResponse();
		TreasureList treasureList = new TreasureList();
		boolean result = false;
		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName())){
			List<Treasure> tesoros = treasuresCreated.get(sessionUser.getName());
			String [] treasureNames = new String[tesoros.size()];
			double [] treasureAlts = new double[tesoros.size()];
			double [] treasureLats = new double[tesoros.size()];
			if(!tesoros.isEmpty()){
				for(int j=tesoros.size()-1, i=0; j>=0 ; j--,i++){
					treasureNames[i] = tesoros.get(j).getName();
					treasureAlts[i] = tesoros.get(j).getAltitude();
					treasureLats[i] = tesoros.get(j).getLatitude();
				}		
			}
			treasureList.setNames(treasureNames);
			treasureList.setAlts(treasureAlts);
			treasureList.setLats(treasureLats);
			result = true;
		}
		treasureList.setResult(result);
		response.set_return(treasureList);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param removeUser 
	 * @return removeUserResponse 
	 */

	public es.upm.fi.sos.RemoveUserResponse removeUser(es.upm.fi.sos.RemoveUser removeUser){
		RemoveUserResponse response = new RemoveUserResponse();
		Response responseParam = new Response();
		String username = removeUser.getArgs0().getUsername();
		Username usernameParam = new Username();
		usernameParam.setName(username);
		ExistUser existUser = new ExistUser();
		existUser.setUsername(usernameParam);
		boolean result = false;

		if(sessionUser!=null && !username.equals(adminUsername) && (sessionUser.getName().equals(username) || sessionUser.getName().equals(adminUsername) )){
			UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser removeUserStub = new UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser();
			RemoveUserE removeUserEStub = new RemoveUserE();
			RemoveUserResponseE removeUserRespEStub = new RemoveUserResponseE();
			removeUserStub.setName(username);
			removeUserEStub.setRemoveUser(removeUserStub);
			try {
				removeUserRespEStub = stub.removeUser(removeUserEStub);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			result = removeUserRespEStub.get_return().getResult();
			if(result) {	
				activeUsers.remove(username);
				treasuresFound.remove(username);
				followers.remove(username);
				for(Treasure t: treasuresCreated.get(username)){
					for(Map.Entry<String, ArrayList<Treasure>> e : treasuresFound.entrySet()){
						for(Treasure t1 : e.getValue()){
							if(t.getName().equals(t1.getName()) && t.getLatitude() == t1.getLatitude() && t.getAltitude() == t1.getAltitude()){
								treasuresFound.get(e.getKey()).remove(t1);
								break;
							}		
						}	
						System.out.println("contnuamos iterando " + e.getKey());
					}
				}
				treasuresCreated.remove(username);
				for(Map.Entry<String, ArrayList<String>> f : followers.entrySet()){
					f.getValue().remove(username);
				}
				System.out.println("El usuario " + username + " ha sido eliminado correctamente");
			}
			else System.out.println("El usuario " + username + " no ha podido ser eliminado");
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param addFollower 
	 * @return addFollowerResponse 
	 */

	public es.upm.fi.sos.AddFollowerResponse addFollower(es.upm.fi.sos.AddFollower addFollower){
		AddFollowerResponse response = new AddFollowerResponse();
		Response responseParam = new Response();
		String followerUsername = addFollower.getArgs0().getUsername();
		Username usernameParam = new Username();
		usernameParam.setName(followerUsername);

		boolean result = false;

		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName()) && activeUsers.containsKey(followerUsername)){
			String usernameSession = sessionUser.getName();
			if(!followers.get(usernameSession).contains(followerUsername)){
				followers.get(usernameSession).add(followerUsername);
				result = true;
			}
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param createTreasure 
	 * @return createTreasureResponse 
	 */

	public es.upm.fi.sos.CreateTreasureResponse createTreasure(es.upm.fi.sos.CreateTreasure createTreasure){
		CreateTreasureResponse response = new CreateTreasureResponse();
		Response responseParam = new Response();
		String treasureName = createTreasure.getArgs0().getName();
		double treasureAltitude = createTreasure.getArgs0().getAltitude();
		double treasureLatitude = createTreasure.getArgs0().getLatitude();
		boolean result = false;
		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName())){
			String usernameSession = sessionUser.getName();
			List<Treasure> tesoros = treasuresCreated.get(usernameSession);
			Treasure treasure = null;
			for(Treasure t : tesoros){
				if(t.getName().equals(treasureName)){
					System.out.println("Encuentra tesoro en el mapa");
					treasure=t;
					break;
				}		
			}	
			if(treasure==null){
				System.out.println("Tesoro no existe");
				treasure = new Treasure();
				treasure.setName(treasureName);
				treasure.setAltitude(treasureAltitude);
				treasure.setLatitude(treasureLatitude);
				treasuresCreated.get(usernameSession).add(treasure);
			}else{
				System.out.println("Tesoro existe");
				treasure.setAltitude(treasureAltitude);
				treasure.setLatitude(treasureLatitude);
				treasuresCreated.get(sessionUser.getName()).remove(treasure);
				treasuresCreated.get(sessionUser.getName()).add(treasure);
			}
			result = true;
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param findTreasure 
	 * @return findTreasureResponse 
	 */

	public es.upm.fi.sos.FindTreasureResponse findTreasure(es.upm.fi.sos.FindTreasure findTreasure){
		FindTreasureResponse response = new FindTreasureResponse();
		Response responseParam = new Response();
		String treasureName = findTreasure.getArgs0().getName();
		double treasureAltitude = findTreasure.getArgs0().getAltitude();
		double treasureLatitude = findTreasure.getArgs0().getLatitude();
		boolean result = false;
		if(sessionUser!=null && activeUsers.containsKey(sessionUser.getName())){
			String usernameSession = sessionUser.getName();
			Treasure treasure = new Treasure();
			treasure.setName(treasureName);
			treasure.setAltitude(treasureAltitude);
			treasure.setLatitude(treasureLatitude);
			for(Map.Entry<String, ArrayList<Treasure>> e : treasuresCreated.entrySet()){
				for(Treasure t : e.getValue()){
					if(t.getName().equals(treasureName) && t.getLatitude() == treasureLatitude && t.getAltitude() == treasureAltitude){
						System.out.println("Encuentra tesoro en el mapa");
						treasuresFound.get(usernameSession).add(treasure);
						result=true;
						break;
					}		
				}	
				System.out.println("contnuamos iterando " + e.getKey());
			}
			
			
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param addUser 
	 * @return addUserResponse 
	 */

	public es.upm.fi.sos.AddUserResponse addUser(es.upm.fi.sos.AddUser addUser){
		AddUserResponse response = new AddUserResponse();
		UPMAuthenticationAuthorizationWSSkeletonStub.AddUser addUserStub = new UPMAuthenticationAuthorizationWSSkeletonStub.AddUser();
		UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse addUserResponseStub = new UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse();
		es.upm.fi.sos.model.xsd.AddUserResponse responseParam = new es.upm.fi.sos.model.xsd.AddUserResponse();
		String username = addUser.getArgs0().getUsername();
		UserBackEnd userParams = new UserBackEnd();
		userParams.setName(username);
		addUserStub.setUser(userParams);
		boolean result = false;
		if(sessionUser!=null && sessionUser.getName().equals(adminUsername)){
			try {
				addUserResponseStub = stub.addUser(addUserStub);

			} catch (RemoteException e) {
				e.printStackTrace();
			}
			result = addUserResponseStub.get_return().getResult();
			if(result){
				activeUsers.put(username, addUserResponseStub.get_return().getPassword());
				followers.put(username, new ArrayList<String>());
				treasuresCreated.put(username, new ArrayList<Treasure>());
				treasuresFound.put(username, new ArrayList<Treasure>());
				responseParam.setPwd(addUserResponseStub.get_return().getPassword());
			}else System.out.println("El usuario: "+username + " ya existe");
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);

		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyFollowerTreasuresCreated 
	 * @return getMyFollowerTreasuresCreatedResponse 
	 */

	public es.upm.fi.sos.GetMyFollowerTreasuresCreatedResponse getMyFollowerTreasuresCreated(es.upm.fi.sos.GetMyFollowerTreasuresCreated getMyFollowerTreasuresCreated){
		GetMyFollowerTreasuresCreatedResponse response = new GetMyFollowerTreasuresCreatedResponse();
		TreasureList treasureList = new TreasureList();
		String followerUsername = getMyFollowerTreasuresCreated.getArgs0().getUsername();
		boolean result = false;
		if(sessionUser != null && activeUsers.containsKey(sessionUser.getName()) && activeUsers.containsKey(followerUsername) && followers.get(sessionUser.getName()).contains(followerUsername)){
			List<Treasure> tesoros = treasuresCreated.get(followerUsername);
			String [] treasureNames = new String[tesoros.size()];
			double [] treasureAlts = new double[tesoros.size()];
			double [] treasureLats = new double[tesoros.size()];	
			if(!tesoros.isEmpty()){
				for(int j=tesoros.size()-1, i=0; j>=0 ; j--,i++){
					treasureNames[i] = tesoros.get(j).getName();
					treasureAlts[i] = tesoros.get(j).getAltitude();
					treasureLats[i] = tesoros.get(j).getLatitude();
				}
			}
			treasureList.setNames(treasureNames);
			treasureList.setAlts(treasureAlts);
			treasureList.setLats(treasureLats);
			result = true;	
		}
		treasureList.setResult(result);
		response.set_return(treasureList);
		return response;
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param changePassword 
	 * @return changePasswordResponse 
	 */

	public es.upm.fi.sos.ChangePasswordResponse changePassword(es.upm.fi.sos.ChangePassword changePassword){
		ChangePasswordResponse response = new ChangePasswordResponse();
		Response responseParam = new Response();	
		String newPwd = changePassword.getArgs0().getNewpwd();
		String oldPwd = changePassword.getArgs0().getOldpwd();
		UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword changePwdStub = new UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword();
		ChangePasswordBackEnd changePwdParam = new ChangePasswordBackEnd();
		ChangePasswordResponseE changePwdRespEStub = new ChangePasswordResponseE();
		boolean result = false;
		if(sessionUser==null){
			responseParam.setResponse(result);
			response.set_return(responseParam);
			System.out.println("Error changepwd, no hay sesiones activas");
			return response;
		}
		String usernameSession = sessionUser.getName();
		String pwdSession = sessionUser.getPwd();
		if(usernameSession.equals(adminUsername) && pwdSession.equals(oldPwd)){
			adminPwd = newPwd;
			result = true;
			sessionUser.setPwd(newPwd);
			System.out.println("Contraseña cambiada para admin, nueva pwd : " + newPwd);
		}else {
			if(activeUsers.containsKey(usernameSession)){
				if (oldPwd.equals(sessionUser.getPwd()))oldPwd = activeUsers.get(usernameSession); 
				changePwdParam.setName(usernameSession);
				changePwdParam.setNewpwd(newPwd);
				changePwdParam.setOldpwd(oldPwd);
				changePwdStub.setChangePassword(changePwdParam);
				try {
					changePwdRespEStub = stub.changePassword(changePwdStub);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				result = changePwdRespEStub.get_return().getResult();
				if (result) {
					activeUsers.put(usernameSession,newPwd);
					System.out.println("Contraseña cambiada para " + usernameSession + ", nueva pwd :" + newPwd);
				}else System.out.println("Error changePwd, las contraseñas no coinciden");		

			}else System.out.println("Error changePwd, el usuario no existe");
		}
		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param login 
	 * @return loginResponse 
	 */

	public es.upm.fi.sos.LoginResponse login(es.upm.fi.sos.Login login){
		LoginResponse response = new LoginResponse();
		Response responseParam = new Response();
		String username = login.getArgs0().getName(), pwd = login.getArgs0().getPwd();
		boolean result = false;

		if(sessionUser!=null){
			if(sessionUser.getName().equals(username)) {
				System.out.println(username + " cuenta con " + sesionesActivas + " sesiones activas");
				pwd = sessionUser.getPwd();
			} else{
				responseParam.setResponse(result);
				response.set_return(responseParam);
				System.out.println("EL usuario " +sessionUser.getName() +" autenticado no puede hacer login con usuario " + username);
				return response;
			}
		}
		if(username.equals(adminUsername) && pwd.equals(adminPwd)){
			sessionUser = new User();
			sessionUser.setName(username);
			sessionUser.setPwd(pwd);
			result = true;
			sesionesActivas++;
			System.out.println("Admin se ha logeado correctamente");
		}else{
			if(activeUsers.containsKey(username)){
				LoginBackEnd loginParams = new LoginBackEnd();
				loginParams.setName(username);
				loginParams.setPassword(pwd);
				UPMAuthenticationAuthorizationWSSkeletonStub.Login loginStub = new UPMAuthenticationAuthorizationWSSkeletonStub.Login();
				UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse loginRespStub = new UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse();
				loginStub.setLogin(loginParams);
				try {			
					loginRespStub = stub.login(loginStub);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				result = loginRespStub.get_return().getResult();	
				if(result){		
					if(sessionUser!=null)sesionesActivas++;
					else {
						sessionUser = new User();
						sessionUser.setName(username);
						sessionUser.setPwd(pwd);	
						sesionesActivas++;
					}
					System.out.println(username + " se ha logeado correctamente");
				}else System.out.println("Error login, contraseña incorrecta");		
			}else System.out.println("Error login, el usuario " + username + " no existe");		
		}

		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;
	}

}