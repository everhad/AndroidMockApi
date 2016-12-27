package com.tiro.datamockapidemo.mockapi;

/**
 * A default implement of IMockApiStrategy.
 * Which would return kinds of responseResult in turns.
 */
public class WheelApiStrategy implements IMockApiStrategy {

    @Override
    public void onResponse(int callCount, Response out) {
        if (out == null) return;

        int step = callCount % 10;

        switch (step) {
            case 0:
            case 1:
            case 2:
            case 3:
                out.state = Response.STATE_SUCCESS;
                break;
            case 4:
            case 5:
                out.state = Response.STATE_SERVER_ERROR;
                break;
            case 6:
            case 7:
                out.state = Response.STATE_SUCCESS;
                break;
            case 8:
            case 9:
                out.state = Response.STATE_NETWORK_ERROR;
                break;
        }

        out.delayMillis = 700;
    }
}
