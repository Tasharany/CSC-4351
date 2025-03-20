int main(void) {
    char *msg = "Test\n";
    int x = 0xFF + 0777;
    while(x > 0) {
        x >>= 1;
        /* comment */
        if(x == 0x0F) break;
    }
    return 0;
}