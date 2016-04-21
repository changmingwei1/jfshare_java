package com.jfshare.buyer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class RLoginName implements java.io.Serializable {

    private static final long serialVersionUID = -4415279469780082174L;

    static final Logger LOGGER = LoggerFactory.getLogger(RLoginName.class);

    /** Gets a new id.
     * @return the new id
     */
    public static String getRLoginName(int thirdType){
        return new RLoginName(thirdType).toString();
    }

    /** Create a new object id.
     */
    public RLoginName(int thirdType){
        _time = (int) (System.currentTimeMillis() / 1000);
        _thirdType = thirdType;
        _inc = _nextInc.getAndIncrement();
    }

    public String toStringObj(){
        byte b[] = toByteArray();

        StringBuilder buf = new StringBuilder(16);

        for ( int i=0; i<b.length; i++ ){
            int x = b[i] & 0xFF;
            String s = Integer.toHexString( x );
            if ( s.length() == 1 )
                buf.append( "0" );
            buf.append( s );
        }

        return buf.toString();
    }

    public byte[] toByteArray(){
        byte b[] = new byte[8];
        ByteBuffer bb = ByteBuffer.wrap( b );
        // by default BB is big endian like we need
        bb.put((byte) (_thirdType & 0xFF));
        bb.putInt(_time);
        bb.put((byte) ((_inc >> 16) ^ 0xFF));
        bb.put((byte) ((_inc >> 8) ^ 0xFF));
        bb.put((byte) (_inc ^ 0xFF));
        return b;
    }

    public String toString(){
        return _Name_PREFIX + toStringObj();
    }

    private static AtomicInteger _nextInc = new AtomicInteger( (new java.util.Random()).nextInt() );
    final int _time;
    final int _thirdType;
    final int _inc;
    private final static String _Name_PREFIX = "u_";

    public static void main(String[] str) {
        LOGGER.info(RLoginName.getRLoginName(1)+"");
    }
}

