package com.example.philcidr;

/**
 * Created by TheArgus on 4/18/2015.
 */

import android.util.Log;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.lang.Math;

public class Network {

    public static final int IP_ADDRESS_SIZE = 32;       //Define size of an IP Address
    public static final int MASK_BITS = 0;              //Define index of mask bits (in second dimension of theArray[][])
    public static final int SUBNET_BITS = 1;            //Define index of subnet bits (in second dimension of theArray[][])
    public static final int HOST_BITS = 2;              //Define index of host bits (in second dimension of theArray[][])
    public static final int NUM_SUBNETS = 3;            //Define index of number of subnets (in second dimension of theArray[][])
    public static final int HOSTS_PER_SUBNET = 4;       //Define index of number of hosts per subnet (in second dimension of theArray[][])

    public static byte[] networkAddress;                //This byte array contains the 4 bytes of main network address being subnetted
    public static byte[] subnetMask;                    //This byte array contains the 4 bytes of the subnet mask
    public static byte[] netMask;                       //This byte array contains the 4 bytes of the net mask
    public static int blockBits;                        //How many bits are used to reference the external CIDR block
    public static int maskBits;                         //How many bits are used to reference the internal subnet mask???
    public static int subnetBits;                       //How many bits are used to reference the subnet
    //public static int arrayIndex = 0;                   //arrayIndex is never used???

    //public static int startMask = 0xFFFFFFFF;
    //public static int netMask_integer = startMask << (IP_ADDRESS_SIZE-blockBits);

    //public static int subnetMask_integer = startMask << maskBits;
    //public static byte[] netMask = ByteBuffer.allocate(4).putInt(1695609641).array();
    public static int[][] theArray;


    // This loop sets up everything in the main array, which holds all the data on possible subnet configurations for a given Network
    public static void loop() {
        try {
            int maskBits = Network.blockBits;
            int index = 0;
            while (maskBits <= (Network.IP_ADDRESS_SIZE - 2)) {
                Network.theArray[index][Network.MASK_BITS] = maskBits;
                Network.theArray[index][Network.SUBNET_BITS] = maskBits - Network.blockBits;
                Network.theArray[index][Network.HOST_BITS] = Network.IP_ADDRESS_SIZE - maskBits;
                Network.theArray[index][Network.NUM_SUBNETS] = (int) Math.pow(2, (double) Network.theArray[index][Network.SUBNET_BITS]);
                Network.theArray[index][Network.HOSTS_PER_SUBNET] = ((int) Math.pow(2, (double) theArray[index][Network.HOST_BITS])) - 2;
                maskBits++;
                index++;
            }
        }
        catch(Exception e){
            Log.d("Network Exception", e.getMessage());
        }
    }

    public static void setNetworkAddress(int one, int two, int three, int four) {
        byte[] asByteArray = new byte[]{
                (byte)one,
                (byte)two,
                (byte)three,
                (byte)four
        };
        int addrAsInteger = getIntFromByteArray(asByteArray);
        int netMaskAsInteger = getIntFromByteArray(netMask);
        int withMask = addrAsInteger & netMaskAsInteger;
        networkAddress = getByteArrayFromInt(withMask);
    }

    /**
     * This setNetworkAddress is used in the NetworkConfigActivity to initialize the network's
     * address. This is the primary network address, the root address of the network which is
     * to be subnetted.
     *
     * TODO: This should really be done better, and some of the methods need to be cleaned up
     *
     * @param one string representation of the value of the first byte
     * @param two string representation of the value of the second byte
     * @param three string representation of the value of the third byte
     * @param four string representation of the value of the fourth byte
     */
    public static void setNetworkAddress(String one, String two, String three, String four) {
        /**
         * this is the network address represented as an array of bytes, as input by the user
         * this initial version of the network address has not had the proper mask applied
         * to it yet.
         */
        byte[] asByteArray = new byte[]{
                (byte)Integer.parseInt(one),
                (byte)Integer.parseInt(two),
                (byte)Integer.parseInt(three),
                (byte)Integer.parseInt(four)
        };
        //get the user input network address as an integer
        int addrAsInteger = getIntFromByteArray(asByteArray);

        //get the net mask as an integer
        int netMaskAsInteger = getIntFromByteArray(netMask);

        //perform a bitwise AND on the user input address and the appropriate mask
        int withMask = addrAsInteger & netMaskAsInteger;

        //get a new byte array from the integer version
        networkAddress = getByteArrayFromInt(withMask);

    }

    /**
     * TODO: consider re-naming this method.
     * TODO: merge this and loop()?
     * This method is called once in the NetworkConfigActivity.
     * Network.blockBits is distinct from Network.maskBits.
     *
     * Network.blockBits is the number of bits which comprise the CIDR block allocated to the
     * network in question which is being subnetted.
     *
     * Network.maskBits is all of the bits which are not part of a specific host address space.
     * That is to say, the Block Bits and the Subnet Bits.
     *
     * For the purposes of initializing the Network data, on this first and single call, the
     * maskBits and the blockBits will be equivalant in number. Later, the number of maskBits
     * may be greater than or equal to the number of blockBits but the number of blockBits will
     * never change unless the Network is re-configured.
     * @param blockBits
     */
    public static void initNetwork(int blockBits){
            Network.maskBits = blockBits;
            Network.blockBits = blockBits;
            int startMask = 0xFFFFFFFF;
            int netMask_integer = startMask << (IP_ADDRESS_SIZE - blockBits);
            setSubnetBits();
            Network.netMask = getByteArrayFromInt(netMask_integer);
            Network.theArray = new int[Network.IP_ADDRESS_SIZE - (Network.blockBits + 1)][5];
            Network.loop();
        }

    public static void setMaskBits(int maskBits) {
        Network.maskBits = maskBits;
        int startMask = 0xFFFFFFFF;
        int subnetMask_integer = startMask << (IP_ADDRESS_SIZE - Network.maskBits);
        Network.subnetMask = getByteArrayFromInt(subnetMask_integer);
        setSubnetBits();
    }

    public static String[] getStringArray(byte[] byteArray) {
        int index = 0;
        String[] stringArray = new String[byteArray.length];
        Integer unsignedInt;
        while (index < byteArray.length) {
            unsignedInt = getByteAsUnsignedInt(byteArray[index]);
            stringArray[index] = unsignedInt.toString();
            index ++;
        }
        return stringArray;
    }

    public static int getIntFromByteArray (byte[] byteArray) {
        return ByteBuffer.wrap(byteArray).getInt();
    }

    public static long getLongFromByteArray (byte[] byteArray) {
        return ByteBuffer.wrap(byteArray).getLong();
    }

    public static byte[] getByteArrayFromInt (int theInteger) {
        return ByteBuffer.allocate(4).putInt(theInteger).array();
    }

    public static void setSubnetBits() {
        subnetBits = maskBits - blockBits;
    }

    public static int hostValueAsInt(int index) {
        return index;
    }

    public static int subnetValueAsInt(int index) {
        return index << (IP_ADDRESS_SIZE - maskBits);
    }

    public static byte[] getIPBySubnetHost(int subnetIndex, int hostIndex) {
        int subnet_int = subnetValueAsInt(subnetIndex);
        int host_int = hostValueAsInt(hostIndex);
        int netAddress_int = getIntFromByteArray(Network.networkAddress);
        int ip_int = subnet_int + host_int + netAddress_int;

        //long subnetIndex_long = (long)subnetIndex & 0x00000000ffffffff;
        //long hostIndex_long = (long)hostIndex & 0x00000000ffffffff;
        //int networkAddress_int = getIntFromByteArray(networkAddress);
        //long networkAddress_long = (long)networkAddress_int & 0xffffffff;
        //long ipAsLong = subnetIndex_long + (hostIndex_long<<hostBits) + networkAddress_long;
        return getByteArrayFromInt(ip_int);
    }

    public static String getHostIndexByIp(byte[] ip) {
        Integer ip_int = getIntFromByteArray(ip);
        Integer subnetMask_int = getIntFromByteArray(subnetMask);
        Integer hostIndex_int = ip_int & (~subnetMask_int);
        return hostIndex_int.toString();
    }

    public static String getSubnetIndexByIp(byte[] ip) {
        Integer ip_int = getIntFromByteArray(ip);
        Integer netMask_int = getIntFromByteArray(netMask);
        Integer subnetIndex_int = (ip_int & (~netMask_int))>>>(IP_ADDRESS_SIZE - maskBits);
        return subnetIndex_int.toString();
    }

    public static byte[] IPFromStrings(String one, String two, String three, String four) {
        return new byte[]{
                (byte)Integer.parseInt(one),
                (byte)Integer.parseInt(two),
                (byte)Integer.parseInt(three),
                (byte)Integer.parseInt(four)
        };
    }

    public static void setSubnetMask(byte[] mask){
        Network.subnetMask = mask;
    }

    public static int getMaskBits() {
        return maskBits;
    }

    public static int getHostBits() {
        return Network.IP_ADDRESS_SIZE - Network.maskBits;
    }

    public static int getByteAsUnsignedInt (byte theByte) {
        return (((Byte)theByte).intValue() & 0x000000ff);
    }

}
