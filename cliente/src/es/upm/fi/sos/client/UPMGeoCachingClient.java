package es.upm.fi.sos.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.client.UPMGeoCachingStub.*;
public class UPMGeoCachingClient {


	public static String crearUsuario(UPMGeoCachingStub stub, String nombreUsuario){
		try {
			AddUser user = new AddUser();
			Username name = new Username();
			name.setUsername(nombreUsuario);
			user.setArgs0(name);
			AddUserResponseE responseE;
			responseE = stub.addUser(user);
			AddUserResponse response = responseE.get_return();
			if(response.getResponse()){
				System.out.println("El usuario "+nombreUsuario+" ha sido añadido correctamente, con contraseña: "+response.getPwd());
				return response.getPwd();
			}
			else{
				System.out.println("El usuario "+nombreUsuario+" ya esta añadido.");
				return response.getPwd();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void login(UPMGeoCachingStub stub, String usuario, String pwd){
		try {
			Login log = new Login();
			User u = new User();
			u.setName(usuario);
			u.setPwd(pwd);
			log.setArgs0(u);
			LoginResponse response;
			response = stub.login(log);
			response.get_return().getResponse();
			if(response.get_return().getResponse()){
				System.out.println("El usuario "+usuario+" ha hecho login correctamente.");
			}
			else{
				System.out.println("El ususario "+usuario+" no ha podido hacer login.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void logout(UPMGeoCachingStub stub){
		try {
			Logout log = new Logout();
			stub.logout(log);
			System.out.println("Se ha hecho logout correctamente.");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarUsuario(UPMGeoCachingStub stub, String nombreUsuario){
		try {
			RemoveUser user = new RemoveUser();
			Username name = new Username();
			name.setUsername(nombreUsuario);
			user.setArgs0(name);
			RemoveUserResponse response;
			response = stub.removeUser(user);
			if(response.get_return().getResponse()){
				System.out.println("El usuario "+nombreUsuario+" se ha eliminado correctamente.");
			}
			else{
				System.out.println("El usuario "+nombreUsuario+" no se ha podido eliminar.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void cambiarPwd(UPMGeoCachingStub stub, String oldPwd, String newPwd){
		try {
			ChangePassword chPwd = new ChangePassword();
			PasswordPair pwd=new PasswordPair();
			pwd.setNewpwd(newPwd);
			pwd.setOldpwd(oldPwd);
			chPwd.setArgs0(pwd);
			ChangePasswordResponse response;
			response = stub.changePassword(chPwd);
			if(response.get_return().getResponse()){
				System.out.println("Se ha cambiado correctamente la contraseña.");
			}
			else{
				System.out.println("No se ha cambiado la contraseña.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void addSeguidor(UPMGeoCachingStub stub, String nombreUsuario){
		try {
			AddFollower user = new AddFollower();
			Username name = new Username();
			name.setUsername(nombreUsuario);
			user.setArgs0(name);
			AddFollowerResponse response;
			response = stub.addFollower(user);
			if(response.get_return().getResponse()){
				System.out.println("El usuario "+nombreUsuario+" ha sido añadido a seguidores.");
			}
			else{
				System.out.println("El usuario "+nombreUsuario+" no ha podido ser añadido a seguidores.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void removeSeguidor(UPMGeoCachingStub stub, String nombreUsuario){
		try {
			RemoveFollower user = new RemoveFollower();
			Username name = new Username();
			name.setUsername(nombreUsuario);
			user.setArgs0(name);
			RemoveFollowerResponse response;
			response = stub.removeFollower(user);
			if(response.get_return().getResponse()){
				System.out.println("El usuario "+nombreUsuario+" ha sido borrado de seguidores.");
			}
			else{
				System.out.println("El usuario "+nombreUsuario+" no ha podido ser borrado se seguidores.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static String[] getMyFollowers(UPMGeoCachingStub stub){
		try {
			GetMyFollowers followers = new GetMyFollowers();
			GetMyFollowersResponse response;
			response = stub.getMyFollowers(followers);
			if(response.get_return().getResult()){
				System.out.println("Lista de seguidores: ");
				String [] names=response.get_return().getFollowers();
				if(names!=null){
					for(int i=0; i<response.get_return().getFollowers().length; i++){
						System.out.println("	Nombre: "+names[i]);
					}	
				}
				return response.get_return().getFollowers();
			}
			else{
				System.out.println("No se ha podido obtener la lista de seguidores.");
				return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void crearTesoro(UPMGeoCachingStub stub, String nombreTesoro, double latitud, double altitud){
		try {
			CreateTreasure creaTesoro = new CreateTreasure();
			Treasure tesoro =new Treasure();
			tesoro.setAltitude(altitud);
			tesoro.setLatitude(latitud);
			tesoro.setName(nombreTesoro);
			creaTesoro.setArgs0(tesoro);
			CreateTreasureResponse response;
			response = stub.createTreasure(creaTesoro);
			if(response.get_return().getResponse()){
				System.out.println("El tesoro "+nombreTesoro+" con latitud: "+latitud+" y altitud: "+altitud+" ha sido creado con exito.");
			}
			else{
				System.out.println("El tesoro "+nombreTesoro+" no ha podido ser creado.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void encontrarTesoro(UPMGeoCachingStub stub, String nombreTesoro, double latitud, double altitud){
		try {
			FindTreasure encuentraTesoro = new FindTreasure();
			Treasure tesoro =new Treasure();
			tesoro.setAltitude(altitud);
			tesoro.setLatitude(latitud);
			tesoro.setName(nombreTesoro);
			encuentraTesoro.setArgs0(tesoro);
			FindTreasureResponse response;
			response = stub.findTreasure(encuentraTesoro);
			if(response.get_return().getResponse()){
				System.out.println("El tesoro "+nombreTesoro+" con latitud: "+latitud+" y altitud: "+altitud+" ha sido añadido a encontrados con exito.");
			}
			else{
				System.out.println("El tesoro "+nombreTesoro+" no ha podido ser añadido a encontrados.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static String[] getTesorosEncontrados(UPMGeoCachingStub stub){
		try {
			GetMyTreasuresFound tesorosEncontrados = new GetMyTreasuresFound();
			GetMyTreasuresFoundResponse response;
			response = stub.getMyTreasuresFound(tesorosEncontrados);
			response.get_return().getNames();
			if(response.get_return().getResult()){
				System.out.println("Lista de tesoros encontrados: ");
				String [] names=response.get_return().getNames();
				double [] latitudes=response.get_return().getLats();
				double [] altitudes=response.get_return().getAlts();
				if(names!=null){
					for(int i=0; i<response.get_return().getNames().length; i++){
						System.out.println("	Nombre: "+names[i]);
						System.out.println("		Altitud: "+altitudes[i]);
						System.out.println("		Latitud: "+latitudes[i]);
					}
				}
				return response.get_return().getNames();
			}
			else{
				System.out.println("No se ha podido obtener la lista de tesoros encontrados.");
				return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] getTesorosCreados(UPMGeoCachingStub stub){
		try {
			GetMyTreasuresCreated tesorosCreados = new GetMyTreasuresCreated();
			GetMyTreasuresCreatedResponse response;
			response = stub.getMyTreasuresCreated(tesorosCreados);
			response.get_return().getNames();
			if(response.get_return().getResult()){
				System.out.println("Lista de tesoros creados: ");
				String [] names=response.get_return().getNames();
				double [] latitudes=response.get_return().getLats();
				double [] altitudes=response.get_return().getAlts();
				if(names!=null){
					for(int i=0; i<response.get_return().getNames().length; i++){
						System.out.println("	Nombre: "+names[i]);
						System.out.println("		Altitud: "+altitudes[i]);
						System.out.println("		Latitud: "+latitudes[i]);
					}
				}
				return response.get_return().getNames();
			}
			else{
				System.out.println("No se ha podido obtener la lista de tesoros creados:");
				return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] getTesorosCreadosSeguidores(UPMGeoCachingStub stub, String username){
		try {
			GetMyFollowerTreasuresCreated tesorosCreados = new GetMyFollowerTreasuresCreated();
			Username usuario = new Username();
			usuario.setUsername(username);
			tesorosCreados.setArgs0(usuario);
			GetMyFollowerTreasuresCreatedResponse response;
			response = stub.getMyFollowerTreasuresCreated(tesorosCreados);
			response.get_return().getNames();
			if(response.get_return().getResult()){
				System.out.println("Lista de tesoros creados por "+ username+": ");
				String [] names=response.get_return().getNames();
				double [] latitudes=response.get_return().getLats();
				double [] altitudes=response.get_return().getAlts();
				if(names!=null){
					for(int i=0; i<response.get_return().getNames().length; i++){
						System.out.println("	Nombre: "+names[i]);
						System.out.println("		Altitud: "+altitudes[i]);
						System.out.println("		Latitud: "+latitudes[i]);
					}
				}
				return response.get_return().getNames();
			}
			else{
				System.out.println("No se ha podido obtener la lista de tesoros creados por "+username);
				return null;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}	

	public static void main(String[] args){
		UPMGeoCachingStub stub;
		UPMGeoCachingStub stub1;
		try {
			stub = new UPMGeoCachingStub();
			stub._getServiceClient().engageModule("addressing");
			stub._getServiceClient().getOptions().setManageSession(true);
			
			stub1 = new UPMGeoCachingStub();
			stub1._getServiceClient().engageModule("addressing");
			stub1._getServiceClient().getOptions().setManageSession(true);
			
			login(stub, "admin", "admin");	
			String pwdcarlosCollaguazo=crearUsuario(stub, "carlosCollaguazo");
			String pwdOlaf=crearUsuario(stub, "olaf");
			String pwdjuanJimenez=crearUsuario(stub, "juanJimenez");
			String pwdmateoEcheverry=crearUsuario(stub, "mateoEcheverry");
			String pwdIwi=crearUsuario(stub, "iwi");
			logout(stub);
			System.out.println();
			
			System.out.println();
			login(stub1, "carlosCollaguazo", pwdcarlosCollaguazo);
			login(stub1, "carlosCollaguazo", pwdcarlosCollaguazo);
			login(stub1, "carlosCollaguazo", pwdcarlosCollaguazo);
			cambiarPwd(stub1, pwdcarlosCollaguazo, "nueva_pwd1");
			cambiarPwd(stub1, pwdcarlosCollaguazo, "nueva_pwd2");
			cambiarPwd(stub1, pwdcarlosCollaguazo, "nueva_pwd3");
			logout(stub1);
			logout(stub1);
			logout(stub1);
			System.out.println();
			
			System.out.println();
			login(stub, "carlosCollaguazo", pwdcarlosCollaguazo);
			login(stub, "carlosCollaguazo", "nueva_pwd3");
			addSeguidor(stub, "olaf");
			addSeguidor(stub, "juanJimenez");
			addSeguidor(stub, "ramon");
			addSeguidor(stub, "iwi");
			getMyFollowers(stub);
			removeSeguidor(stub, "iwi");
			getMyFollowers(stub);
			crearTesoro(stub, "tesoro1", 2.0, 2.0);
			crearTesoro(stub, "tesoro2", 3.0, 3.0);
			crearTesoro(stub, "tesoro3", 4.0, 4.0);
			getTesorosCreados(stub);
			getTesorosEncontrados(stub);
			encontrarTesoro(stub, "tesoro1", 2.0, 2.0);
			encontrarTesoro(stub, "tesoro2", 3.0, 3.0);
			crearTesoro(stub, "tesoro3", 5.0, 9.0);
			getTesorosCreados(stub);
			getTesorosEncontrados(stub);
			logout(stub);
			System.out.println();
		
			System.out.println();
			login(stub1, "olaf", pwdOlaf);
			addSeguidor(stub1, "carlosCollaguazo");
			getMyFollowers(stub1);
			getTesorosCreadosSeguidores(stub1, "carlosCollaguazo");
			encontrarTesoro(stub1, "tesoro1", 2.0, 2.0);
			encontrarTesoro(stub1, "tesoro2", 3.0, 3.0);
			encontrarTesoro(stub1, "tesoro3", 4.0, 4.0);
			getTesorosEncontrados(stub1);
			getTesorosCreados(stub1);
			logout(stub1);
			System.out.println();
			
			System.out.println();
			login(stub1, "admin", "admin");
			eliminarUsuario(stub1, "carlosCollaguazo");
			logout(stub1);
			System.out.println();
			
			System.out.println();
			login(stub, "olaf", pwdOlaf);
			getTesorosEncontrados(stub);
			getTesorosCreadosSeguidores(stub, "carlosCollaguazo");
			getMyFollowers(stub);
			logout(stub);
			System.out.println();
			
			System.out.println();
			login(stub1, "admin", "admin");	
			eliminarUsuario(stub1, "olaf");
			eliminarUsuario(stub1, "juanJimenez");
			eliminarUsuario(stub1, "mateoEcheverry");
			eliminarUsuario(stub1, "iwi");
			logout(stub1);

			

		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}