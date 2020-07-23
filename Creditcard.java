/*  PATADIYA CHIRAG
	OOP-PROJECT
	CREDIT CARD MANAGEMENT SYSTEM
*/

import java.util.*;
import java.io.File;
import java.io.IOException; 
import java.io.RandomAccessFile;
import java.util.Random;
abstract class Info{
	abstract String creditcardgen(String s, int l);//abstract method to generate creditcard
}	
//Coustomer class inherited from Info
class Coustomer extends Info{
	private Random random = new Random(System.currentTimeMillis());
	public String ach_name,acc_no,bin;//variable: account holder name,account no,bank indentification number
	File file;//file reference
	
	
	void information(){//information method
		Scanner sc=new Scanner(System.in);
		System.out.println("Account number: ");
		acc_no=sc.nextLine();
		System.out.println("Account holder name: ");
		ach_name=sc.nextLine();
		try{
		file= new File("info.txt");//file name info.txt
		if (!file.exists()) { 
            // Create a new file if not exists. 
            file.createNewFile(); 
        } 
		RandomAccessFile raf = new RandomAccessFile("info.txt", "rw");
		long length = raf.length();
		System.out.print("Enter bin : ");
		bin=sc.nextLine();
		bin=creditcardgen(bin,16);//credit crad generator method call
		try{
			if(getCheckDigit(bin)==0){
				raf.seek(raf.length());
				raf.writeBytes(acc_no+System.lineSeparator());
				raf.writeBytes(ach_name+System.lineSeparator());
				raf.writeBytes(bin+" | ");
				raf.writeBytes(random(12)+" | "+random(2019,2030)+" | "+random(100,999)+System.lineSeparator());
				raf.writeBytes("------------------------------------------------------------------------"+System.lineSeparator());
			}
		}
		catch(Exception e){
			System.out.println("BIN Error");
		}
		raf.close();
		}
		catch(Exception e){
			System.out.println("Can't create file:"+e);
		}		
	}
	
	public String creditcardgen(String bin, int length) {//credit card generator method

        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        //Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }
    int getCheckDigit(String number) {

        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
	int random(int max){// random method to generate random month,year,cvv
		 double x = (Math.random()*((max-1)+1))+1;
		 return (int)x;
	}
	int random(int max , int min){// random method to generate random month,year,cvv
		 double x = (Math.random()*((max-min)+1))+min;
		 return (int)x;
	}
	//search method to search data in file
	void search(String s){
		int flag=0;
		try{
			RandomAccessFile raf = new RandomAccessFile("info.txt", "rw");
			while (raf.getFilePointer() < raf.length()){
				String data=raf.readLine(); 
				if(data.equals(s)){
					flag=1;
					System.out.println(data);
					raf.getFilePointer();
					data=raf.readLine();
					System.out.println(data);
					raf.getFilePointer();
					data=raf.readLine();
					System.out.println(data);
					break;
				}
			}
			raf.close();
			if(flag==0){
				System.out.println("account not found");
			}
		}
		catch(Exception e){
			System.out.println("file not created");
		}
	}
	//delete method to delete data in file
	void deleteData(String s){
		int i=10;
		int flag=0;
		try{
				RandomAccessFile raf1 = new RandomAccessFile("temp.txt", "rw");
				RandomAccessFile raf = new RandomAccessFile("info.txt", "rw");
			while ( raf.getFilePointer()< raf.length()){
				String data=raf.readLine();
				raf.getFilePointer();
				if(data.equals(s)){
					i=3;flag=1;
					raf.getFilePointer();
					continue;
				}
				else if(i>0&&i<4){
					raf.getFilePointer();i--;
					continue;
				}
				else
				{
					
					raf1.writeBytes(data+System.lineSeparator());
				}
			}
			if(flag==0){
			System.out.println("Account not found");
			}
			else{
				raf.seek(0);
				raf1.seek(0);
				long l=raf1.length();
				while(raf1.getFilePointer()< raf1.length()){
					String d=raf1.readLine();
					raf.writeBytes(d+System.lineSeparator());
					raf.getFilePointer();
				}
				raf.setLength(l);
				raf1.close();
				File f=new File("C:\\Users\\Chirag\\Desktop\\javaproject\\temp.txt");
				f.delete();
				raf.close();
			}
		}
		catch(Exception e){
			System.out.println("Deletion Error"+e);
		}
	}
	//edit method to edit data in file
	void editData(String s){
		int flag=0;
		int i=10;
		Scanner in=new Scanner(System.in);
		try{
				RandomAccessFile raf1 = new RandomAccessFile("temp.txt", "rw");
				RandomAccessFile raf = new RandomAccessFile("info.txt", "rw");
				System.out.print("Enetr new name : ");
				String s2=in.nextLine();
			while ( raf.getFilePointer()< raf.length()){
				String data=raf.readLine();
				raf.getFilePointer();
				if(data.equals(s)){
					raf1.writeBytes(data+System.lineSeparator());
					raf1.writeBytes(s2+System.lineSeparator());
					flag=1;
					i=1;
				}
				else if(i>0&&i<2){
					i--;
					continue;
				}
				else
				{
					
					raf1.writeBytes(data+System.lineSeparator());
				}
			}
			if(flag==0){
			System.out.println("Account not found");
			}
			else{
				raf.seek(0);
				raf1.seek(0);
				long l=raf1.length();
				while(raf1.getFilePointer()< raf1.length()){
					String d=raf1.readLine();
					raf.writeBytes(d+System.lineSeparator());
					raf.getFilePointer();
				}
				raf.setLength(l);
				raf1.close();
				File f=new File("temp.txt");
				f.delete();
				raf.close();
			}
		}
		catch(Exception e){
			System.out.println("Error in edditing"+e);
		}
	}
}
// main class Creditcard contain public static void main method
class Creditcard{
	public static void main(String args[]){
		Coustomer c = new Coustomer();// object of Coustomer class
		Scanner sc=new Scanner(System.in);//sacnner object
		Scanner i=new Scanner(System.in);
		String s;
		int n;
		System.out.println("***** Welcome to creditcard management system *****\n");
		System.out.println("1.Enter data \n2.search data \n3.delete data \n4.edit data \n5.exit");
		while(true){
			System.out.print("***CHOICE : ");
			n=sc.nextInt();
			switch(n){
				case 1:
					c.information();
				break;
				case 2:
					System.out.print("enter account no : ");
					s=i.nextLine();
					c.search(s);
				break;
				case 3:
					System.out.print("enter account no : ");
					s=i.nextLine();
					c.deleteData(s);
				break;
				case 4:
					System.out.print("enter account no : ");
					s=i.nextLine();
					c.editData(s);
				break;
				case 5:
					System.exit(0);
				default:
					System.out.println("  INVALID CHOICE!! ");
			}
		}
	}
}