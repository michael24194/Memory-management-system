package memorymanag;

import java.util.ArrayList;
import java.util.Scanner;

public class MemoryManagement {
    public static ArrayList updateAddresses(ArrayList <node> list)
    {
        for (int i = 0; i < list.size(); i++) 
        {
            if (i == 0) 
            {
                list.get(i).startingMemoryAddress = 0;
            } 
            else 
            {
                list.get(i).startingMemoryAddress = list.get(i - 1).startingMemoryAddress + list.get(i - 1).sizeOfWholePartion;
            }
        }
        return list;
    }
    public static ArrayList setMem()
    {
        Scanner cin = new Scanner(System.in);
        ArrayList<node> list = new ArrayList();
        int numberOfPartions;
        System.out.print("Enter Number of Partions : ");
        numberOfPartions = cin.nextInt();
        System.out.println("Enter Size Of partions");
        for (int i = 0; i < numberOfPartions; i++) 
        {
            node n = new node();
            int sizeOfPartion = cin.nextInt();
            n.sizeOfWholePartion = sizeOfPartion;
            list.add(n);
        }
        //setting el adresses
        list = updateAddresses(list);
        return list;
    }
    public static void view(ArrayList<node> list) 
    {
        System.out.println("");
        System.out.println("partion number   sizeOfPartion    strartingMemoryAdress     status");
        for (int j = 0; j < list.size(); j++) 
        {
            System.out.println(j + 1 + "                         " + list.get(j).sizeOfWholePartion + "              " + list.get(j).startingMemoryAddress + "                  " + list.get(j).status);
        }
        System.out.println("");
    }
    public static int allocateFirstFit(int partionSize, ArrayList<node> l) 
    {
        boolean flag = false;
        for (int i = 0; i < l.size(); i++) 
        {
            if (partionSize <= l.get(i).getSizeOfWholePartion() && l.get(i).status == false) 
            {
                l.get(i).status = true;                
                int freeSize = l.get(i).sizeOfWholePartion - partionSize;
                l.get(i).setSizeOfWholePartion(partionSize);        
                node n = new node();
                n.sizeOfWholePartion = freeSize;
                n.startingMemoryAddress = l.get(i).startingMemoryAddress + partionSize;
                n.status = false;
                if (freeSize != 0)
                    l.add(i +1, n);                                
                flag = true;
                return l.get(i).startingMemoryAddress;
            }
        }
        // hna law mfi4 mkan hyrg3 -1
        return -1;
    }
    public static int allocateWrstFit(int partionSize, ArrayList<node> l) 
    {
        int max = 0;
        int index = -1;
        for (int i = 0; i < l.size(); i++) 
        {
            if (l.get(i).sizeOfWholePartion >= max && l.get(i).sizeOfWholePartion >= partionSize && l.get(i).status == false) 
            {
                max = l.get(i).sizeOfWholePartion;
                index = i;
            }
        }
        if (index != -1) 
        {
            l.get(index).status = true;
            int freeSize = l.get(index).sizeOfWholePartion - partionSize;
            l.get(index).setSizeOfWholePartion(partionSize);        
            node n = new node();
            n.sizeOfWholePartion = freeSize;
            n.startingMemoryAddress = l.get(index).startingMemoryAddress + partionSize;
            n.status = false;
            if (freeSize != 0)
                l.add(index +1, n);
            //l.get(index).freeSize = l.get(index).sizeOfWholePartion - partionSize;
            return l.get(index).startingMemoryAddress;
        }
        return -1;
    }
    public static int bestFit(ArrayList<node> list, int ram) 
    {
        ArrayList dif = new ArrayList();
        int min = 9999;
        for (int i=0;i<list.size();i++) 
        {
            int li = list.get(i).sizeOfWholePartion;
            //System.out.println("li : " + li);
            int diff = li - ram;
            if (diff < 0 || list.get(i).status == true) 
            {
                diff = 999;
            }
            if (min > diff) 
            {
                min = diff;
            }
            dif.add(diff);
        }
        if (min == 999) 
        {
            //System.out.println("No available space");
            return -1;
        } 
        else 
        {
            int targetAddress = dif.lastIndexOf(min);
            int freeSize = list.get(targetAddress).sizeOfWholePartion - ram;
            //System.out.println(min + " " + targetAddress);
            list.get(targetAddress).status = true;
            list.get(targetAddress).setSizeOfWholePartion(ram);        
            node n = new node();
            n.sizeOfWholePartion = freeSize;
            n.startingMemoryAddress = list.get(targetAddress).startingMemoryAddress + ram;
            n.status = false;
            if (freeSize != 0)
                list.add(targetAddress +1, n);
            
            return list.get(targetAddress).startingMemoryAddress;
        }
    }
    public static ArrayList defrag(ArrayList<node> list) 
    {
        ArrayList <node> l = new ArrayList(); 
        System.out.println("size of list = " + list.size());
        node n = new node();
        //System.out.println("new partition mem address = " + n.startingMemoryAddress);
        int sizeOfN = 0;
        for (int i=0;i<list.size();i++) 
        {
            if (list.get(i).status == false)
            {
                sizeOfN += list.get(i).sizeOfWholePartion;            
                int x = list.get(i).sizeOfWholePartion;                                
            }
            if (list.get(i).status == true)
                l.add(list.get(i));
        }
        n.startingMemoryAddress = list.get(list.size() - 1).startingMemoryAddress + list.get(list.size() - 1).sizeOfWholePartion;        
        n.sizeOfWholePartion = sizeOfN;
        if (sizeOfN != 0) 
        {
            l.add(n);
        }
        l = updateAddresses(l);
        return l;
        //System.out.println("size of n = " + n.sizeOfWholePartion);
    }
    public static boolean deallocate(ArrayList <node> list,int address)
    {
        for (int i=0;i<list.size();i++)
        {                    
            if (list.get(i).startingMemoryAddress == address && list.get(i).status == true)
            {                        
                list.get(i).status = false;
                return true;
            }            
        }
        return false;
    }
    public static void main(String[] args) 
    {
        Scanner cin = new Scanner(System.in);
        ArrayList<node> list;        
        list = setMem();
        view(list);
        while (true) 
        {
            System.out.println("\nWelcome, If you want to make allocation press 1");
            System.out.println("         If you want to make deallocation press 2");
            System.out.println("         If you want to make defragmentation press 3");
            System.out.println("         If you want to show you current memory press 4");
            int ch;
            System.out.print("Your choice is : ");
            ch = cin.nextInt();
            System.out.println("");
            if (ch == 1) 
            {                                
                System.out.println("1-First Fit");
                System.out.println("2-Worst Fit");
                System.out.println("3-Best Fit");
                System.out.print("Enter your choice : ");
                int choice = cin.nextInt();
                if (choice == 1) 
                {
                    int c = 1;
                    while (c != 0) 
                    {
                        System.out.print("Enter partion size : ");
                        int PartionSize = cin.nextInt();
                        int address = allocateFirstFit(PartionSize, list);
                        System.out.println("address of the allocated partition  = " + address);
                        if (address != -1) 
                        {
                            view(list);
                        } 
                        else 
                        {
                            System.out.println("WARNING : there is no space");
                        }
                        System.out.print("Enter 0 to stop , anything else to continue adding processes : ");
                        c = cin.nextInt();
                    }
                } 
                else if (choice == 2) 
                {
                    int c = 1;
                    while (c != 0) 
                    {
                        System.out.print("Enter partion size : ");
                        int PartionSize = cin.nextInt();
                        int address = allocateWrstFit(PartionSize, list);
                        System.out.println("address of the allocated partition  = " + address);
                        if (address != -1) 
                        {
                            view(list);
                        } 
                        else 
                        {
                            System.out.println("WARNING : there is no space");
                        }
                        System.out.print("Enter 0 to stop , anything else to continue adding processes : ");
                        c = cin.nextInt();
                    }
                } 
                else if (choice == 3) 
                {
                    int c = 1;
                    while (c != 0) 
                    {
                        System.out.print("Enter partion size : ");
                        int PartionSize = cin.nextInt();
                        int address = bestFit(list, PartionSize);
                        System.out.println("address of the allocated partition  = " + address);
                        if (address != -1) 
                        {
                            view(list);
                        } 
                        else 
                        {
                            System.out.println("WARNING : there is no space");
                        }
                        System.out.print("Enter 0 to stop , anything else to continue adding processes : ");
                        c = cin.nextInt();
                    }
                }
            } 
            else if (ch == 2) 
            {                                
                int address;
                System.out.print("Enter the address of the starting memory you want to deallocate : ");
                address = cin.nextInt();
                if (deallocate(list,address) == false)
                {
                    System.out.println("WARNING : either empty memory or wrong address");
                }
                else
                    view(list);
            } 
            else if (ch == 3) 
            {
                list = defrag(list);
                view(list);
            }
            else if (ch == 4)
                view(list);
        }
    }
}
