
package memorymanag;
public class node {

    public int startingMemoryAddress=0;
    public int sizeOfWholePartion=0;
    public boolean status=false;

    public void setStartingMemoryAddress(int startingMemoryAddress) {
        this.startingMemoryAddress = startingMemoryAddress;
    }

    public void setSizeOfWholePartion(int sizeOfWholePartion) {
        this.sizeOfWholePartion = sizeOfWholePartion;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStartingMemoryAddress() {
        return startingMemoryAddress;
    }

    public int getSizeOfWholePartion() {
        return sizeOfWholePartion;
    }

    public boolean isStatus() {
        return status;
    }
}
