package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDTO;
//import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { 
			LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { 
			MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String BEGINNING_MENU_RETURN = "Return to start menu";
	private static final String BEGINNING_MENU_CONTINUE = "Try again";
	private static final String[] 
			EXTRA_LOGIN_LOOP_NAV = {BEGINNING_MENU_CONTINUE, BEGINNING_MENU_RETURN,MENU_OPTION_EXIT};
	private static final String TRANSFER_DO_NOTHING = "Do Nothing";
	private static final String TRANSFER_CONFIRM = "Confirm Request";
	private static final String TRANSFER_REJECT = "Reject Request";
	private static final String TRANSFER_MAIN_MENU = "Back to main menu";
	private static final String[] TRANSFER_OPTIONS = {TRANSFER_DO_NOTHING,TRANSFER_CONFIRM,TRANSFER_REJECT,TRANSFER_MAIN_MENU};
	private static final String IGNORE_REQUESTS_LOGIN = "Ignore and continue";
	private static final String VIEW_REQUESTS_LOGIN = "Show me requests";
	private static final String[] LOGIN_REQUESTS_VIEW = {IGNORE_REQUESTS_LOGIN,VIEW_REQUESTS_LOGIN};
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    //private RestTemplate restTemplate;
    private AccountService accountService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		viewPendingRequests(0);
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests(1);
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		System.out.print("\nYour account ballance is $");
		System.out.print(accountService.getBalance(currentUser));
		System.out.println("");
	}

	private void viewTransferHistory() {
		Transfer[] transfers = accountService.getAllTransfers(currentUser);
		for(Transfer transfer:transfers) {
			System.out.print(transfer.toString());
		}
		if (transfers.length<1) {
			System.out.print("\nIt seems there are no transfers for this account!\n\n");
		}
	}
	
	/*private void checkForPending() {
		Transfer[] transfers = accountService.getPendingRequests(currentUser);
		for(Transfer transfer:transfers) {
			if (transfer.getFromAccount().getId()==currentUser.getUser().getId()) {
				System.out.print(transfer.toStringPendingTheirRequest());
				String choice = (String)console.getChoiceFromOptions(new String[] {"Do Nothing", "Confirm Request", "Reject Request"});}}}
*/
	private void viewPendingRequests(int login) {
		Transfer[] transfers = accountService.getPendingRequests(currentUser);
		//count is to only write the header/title once per Transfer[]
		int count = 0;
		//login will be zero upon just logging in
		if (login==1) {
		for(Transfer transfer:transfers) {
			//USER HAS REQUESTED PAYMENT
			if(transfer.getToAccount().getId()==currentUser.getUser().getId()) {
				count++;
				if (count==1) {
					System.out.print("You are still waiting on these payments : \n\n");
				}
				System.out.print(transfer.toStringPendingYourRequest()); 
			}
		}
		}
		count=0;
		System.out.print("\n");
		for(Transfer transfer:transfers) {
			//SOMEBODY HAS REQUESTED FROM USER
			if(transfer.getFromAccount().getId()==currentUser.getUser().getId()) {
				count++;
				if (count==1) {
					System.out.print("There are requested payments from you : \n\n");
				}if(login==0) {
					String anotherChoice = (String)console.getChoiceFromOptions(LOGIN_REQUESTS_VIEW);
					if(anotherChoice.equals(IGNORE_REQUESTS_LOGIN )) {
						mainMenu();
					}
				}
				System.out.print(transfer.toStringPendingTheirRequest());
				System.out.print("\n");
				String choice = (String)console.getChoiceFromOptions(TRANSFER_OPTIONS);
				
				if(choice.equals(TRANSFER_CONFIRM)) {
					accountService.enactTransfer(currentUser, transfer.getTransferId());
					System.out.println("Request approved.\n");
					accountService.getBalance(currentUser);
				}else if(choice.equals(TRANSFER_REJECT)) {
					accountService.denyTransfer(currentUser, transfer.getTransferId());
					System.out.println("Transfer rejected.\n");
				}else if(choice.equals(TRANSFER_DO_NOTHING)){
					System.out.println("No action taken, but if you owe somebody money, don't put it off. . . :/\n");
				} else {
					mainMenu();
				}
			}
		}
		if (transfers.length<1&login==1) {
			System.out.print("\nIt seems there are no pending requests for this account!\n\n");
		}
		login=1;
}

	private void sendBucks() {
		
		//list users by id and username
		User[] sendList = accountService.listAllUsers(currentUser);
		for(User user:sendList) {
			System.out.println(user.getId()+"  |  "+user.getUsername());
		}
		
		//prompts
		int receiver = console.getUserInputInteger("\nPlease enter the user ID to send to ");
		String amount = console.getUserInput("\nWhat amount would you like to send?");
		BigDecimal transferAmount = new BigDecimal(amount);

		//set transferDTO
		TransferDTO transfer = new TransferDTO();
		transfer.setFromAccount(Math.toIntExact(currentUser.getUser().getId()));
		transfer.setToAccount(receiver);
		transfer.setAmountTransferred(transferAmount);
		System.out.println(accountService.sendTransfer(currentUser, transfer).toString());
	}

	private void requestBucks() {
		User[] requestList = accountService.listAllUsers(currentUser);
		for (User user:requestList) {
			System.out.println(user.getId()+"    |    "+user.getUsername());
		}
		
		//prompts
		int sender = console.getUserInputInteger("\nPlease enter the user ID to request from ");
		String amount = console.getUserInput("\nWhat amount are you requesting");
		BigDecimal transferAmount = new BigDecimal(amount);
		
		//set transferDTO
		TransferDTO transfer = new TransferDTO();
		transfer.setFromAccount(sender);
		transfer.setToAccount(Math.toIntExact(currentUser.getUser().getId()));
		transfer.setAmountTransferred(transferAmount);
		System.out.println(accountService.sendTransfer(currentUser, transfer).toString());		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage()+"\n");
            	loginNavigation();
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage()+"\n");
				loginNavigation();
			}
		}
	}
	private void loginNavigation() {
		String exitLoop = (String)console.getChoiceFromOptions(EXTRA_LOGIN_LOOP_NAV);
		if(exitLoop.equals(BEGINNING_MENU_RETURN)) {
			registerAndLogin();
		}if(exitLoop.equals(MENU_OPTION_EXIT)) {
			exitProgram();
		}
	}
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
/*	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        //~~experimenting~~ :/
	        headers.setBearerAuth(currentUser.getToken());
	        
	        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	        return entity;} 
	private HttpEntity<User> makeUserEntity(User user) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<User> entity = new HttpEntity<>(user, headers);
	        return entity;}*/
}