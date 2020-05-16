class Trie {

    public TreeMap<String, Integer> hm;
    int max;
    
    /** Initialize your data structure here. */
    public Trie() {
        hm = new TreeMap<String, Integer>();
        max = 0;
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        hm.put(word, word.length());
        if(word.length()>max) max=word.length();
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Integer i = hm.get(word); 
        if(i == null) return false;
        return true;
        
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        if(max < prefix.length()) return false;
        int size = hm.size();
        hm.put(prefix, prefix.length());
        if(size == hm.size()) return true;
        String next = hm.higherKey(prefix);
        boolean ans;
        if(next != null && (next.length() > prefix.length()) &&                                                                      prefix.equals(next.substring(0,prefix.length()))) {
            ans = true;
        } else {
            ans = false;
        }
        hm.remove(prefix);
        return ans;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
