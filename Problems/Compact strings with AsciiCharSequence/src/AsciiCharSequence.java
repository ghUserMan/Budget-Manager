public class AsciiCharSequence implements CharSequence {

    byte[] string;

    public AsciiCharSequence(byte[] string) {
        this.string = string;
    }

    @Override
    public int length() {
        return string.length;
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index < string.length) {
            return (char)(string[index] & 255);
        } else {
            throw new StringIndexOutOfBoundsException(index);
        }
    }

    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        int length = this.length();
        checkBoundsBeginEnd(beginIndex, endIndex, length);
//        int subLen = endIndex - beginIndex;
        if (beginIndex == 0 && endIndex == length) {
            return this;
        } else {
            byte[] arr = java.util.Arrays.copyOfRange(string, beginIndex, endIndex) ;
//            for (int i = beginIndex; i < endIndex; i++) {
//                arr[i] = string[i];
//            }
            return new AsciiCharSequence(arr);
        }
    }

    @Override
    public String toString() {
        return new String(string);
    }

    private void checkBoundsBeginEnd(int begin, int end, int length) {
        if (begin < 0 || begin > end || end > length) {
            throw new StringIndexOutOfBoundsException("begin " + begin + ", end " + end + ", length " + length);
        }
    }
}