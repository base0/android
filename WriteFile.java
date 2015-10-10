// write file
// getExternalFilesDir 
// /storage/
// folder will be removed when uninstall the app

try {
    File file;
    file = new File(getExternalFilesDir(null),
            "hello.txt");
    PrintWriter pw = new PrintWriter(
            new FileWriter(file));
    pw.print("hello world");
    pw.close();
} catch (Exception ex) {
    Log.e("gg", ex.toString());
}
