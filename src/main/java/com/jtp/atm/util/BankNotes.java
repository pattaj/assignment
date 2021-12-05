package com.jtp.atm.util;

public class BankNotes {

	public static String getBankType(int bankValue){
        String bankType;
        switch (bankValue){
            case 50:
                bankType = BankNotesEnum.FIFTY.name();
                break;
            case 20:
                bankType = BankNotesEnum.TWENTY.name();
                break;
            case 10:
                bankType = BankNotesEnum.TEN.name();
                break;
            case 5:
                bankType = BankNotesEnum.FIVE.name();
                break;
            default:
                bankType = BankNotesEnum.TWENTY.name();
                break;
        }
        return bankType;
    }
	
}
