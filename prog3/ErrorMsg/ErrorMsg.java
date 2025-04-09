package ErrorMsg;

public class ErrorMsg {
    private String filename;
    public boolean anyErrors;

    public ErrorMsg(String f) {
        filename = f;
        anyErrors = false;
    }

    public void error(int pos, String msg) {
        anyErrors = true;
        System.err.println(filename + ":" + pos + ": " + msg);
    }

    public void newline(int pos) {
        // track line numbers if needed
    }
}
