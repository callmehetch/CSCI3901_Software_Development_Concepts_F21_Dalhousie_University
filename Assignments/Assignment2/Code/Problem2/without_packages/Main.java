
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


import java.util.PriorityQueue;


class Main implements FileCompressor
{
    public static void encodedString(Node root, String str,
                                     Map<Character, String> huffmanCode)
    {
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }

        encodedString(root.left, str + '0', huffmanCode);
        encodedString(root.right, str + '1', huffmanCode);
    }
    public static int decodedString(Node root, int index, StringBuilder sb)
    {
        if (root == null) {
            return index;
        }
        if (isLeaf(root))
        {
            System.out.print(root.ch);
            return index;
        }

        index++;

        root = (sb.charAt(index) == '0') ? root.left : root.right;
        index = decodedString(root, index, sb);
        return index;
    }

    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    public static void codebook(String text)
    {
        if (text == null || text.length() == 0) {
            return;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));
        for (var entry: freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }
        Node root = pq.peek();

        Map<Character, String> huffmanCode = new HashMap<>();
        encodedString(root, "", huffmanCode);
        System.out.println("The original string is: " + text);
        System.out.println("Codebook: " + huffmanCode);
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("The encoded string is: " + sb);
        System.out.print("The decoded string is: ");

        if (isLeaf(root))
        {
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        }
        else {
            int index = -1;
            while (index < sb.length() - 1) {
                index = decodedString(root, index, sb);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String k = scan.next();
        String text = readFile(k);
        codebook(text);
    }

    static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    int level;
    boolean reset;
    @Override
    public boolean encode(String input_filename, int level, boolean reset, String output_filename) {
        this.level = level;
        this.reset = reset;
        String t1 = "";
        try {
             t1 = readFile(input_filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        codebook(t1);
        return false;

    }

    @Override
    public boolean decode(String input_filename, String output_filename) {
        return false;
    }

    @Override
    public Map<Character, String> codebook() {
        return null;
    }
}



