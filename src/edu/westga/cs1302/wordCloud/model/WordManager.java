package edu.westga.cs1302.wordCloud.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class WordManager implements  Collection<WordData>{
	private Map<String, WordData> wordManage;
	private ArrayList<Integer> frequencies;
	private LinkedHashMap<String,WordData> newMap;
	
	public WordManager() {
		this.newMap = new LinkedHashMap<String, WordData>();
		this.frequencies = new ArrayList<Integer>();
		this.wordManage = new HashMap<String, WordData>();
	}
	
	public WordData getFrequencyByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		return this.wordManage.get(word);
	}
	
	public boolean containsWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		return this.wordManage.containsKey(word);
	}
	
	public boolean removeByWord(String word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		WordData removeWord = this.wordManage.remove(word);
		
		return (removeWord != null);
	}
	
	@Override
	public boolean add(WordData data) {
		if (data == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		this.wordManage.put(data.getData(), data);
		return this.wordManage.containsKey(data.getData());	
	}

	@Override
	public boolean addAll(Collection<? extends WordData> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		for (WordData word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}
			this.wordManage.put(word.getData(), word);
		}
		return true;
	}

	@Override
	public void clear() {
		this.wordManage.clear();		
	}

	@Override
	public boolean contains(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null");
		}
		WordData theData = (WordData) word;
		return this.wordManage.containsKey(theData.getData());
	}

	@Override
	public boolean containsAll(Collection<?> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		for (Object word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}
			if (!this.contains(word)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return this.wordManage.isEmpty();
	}

	@Override
	public Iterator<WordData> iterator() {
		return this.wordManage.values().iterator();
	}

	@Override
	public boolean remove(Object word) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null");
		}
		WordData data = (WordData) word;
		WordData removeData = this.wordManage.remove(data.getData());
		return (data == removeData);
	}

	@Override
	public boolean removeAll(Collection<?> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null");
		}
		boolean removedAll = true;

		for (Object word : words) {
			if (word == null) {
				throw new IllegalArgumentException("word should not be null");
			}

			if (!this.remove(word)) {
				removedAll = false;
			}

		}
		return removedAll;
	}
	public Map<String, WordData> getWordManage() {
		return this.wordManage;
	}

	public void setWordManage(Map<String, WordData> wordManage) {
		this.wordManage = wordManage;
	}
	public Map<String, WordData> sortByFrequency() {
		this.frequencies = new ArrayList<Integer>();
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		this.wordManage = new LinkedHashMap<String, WordData>();
		for (Map.Entry<String, WordData> entry : list) {
			this.wordManage.put(entry.getKey(), entry.getValue());
			this.frequencies.add(entry.getValue().getFrequency());
		}
		return this.wordManage;
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return this.wordManage.size();
	}

	@Override
	public Object[] toArray() {
		return this.wordManage.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] word) {
		if (word == null) {
			throw new IllegalArgumentException("array should not be null");
		}
		return this.wordManage.values().toArray(word);
	}

	public Map<String, WordData>  sortDefault() {
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		this.wordManage = new HashMap<String, WordData>();
		for (Map.Entry<String, WordData> entry : list) {
			this.wordManage.put(entry.getKey(), entry.getValue());
		}
		return this.wordManage;	
	}

	public Map<String, WordData> sortMixed() {
		this.frequencies = new ArrayList<Integer>();
		Set<Entry<String, WordData>> set = this.wordManage.entrySet();
		List<Entry<String, WordData>> list = new ArrayList<Entry<String, WordData>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return -1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return 1;
				}
				return 0;
			}
		});
		Set<Entry<String, WordData>> setRe = this.wordManage.entrySet();
		List<Entry<String, WordData>> listRe = new ArrayList<Entry<String, WordData>>(setRe);
		Collections.sort(listRe, new Comparator<Map.Entry<String, WordData>>() {

			@Override
			public int compare(Entry<String, WordData> oldWord, Entry<String, WordData> newWord) {
				if (oldWord.getValue().getFrequency() < newWord.getValue().getFrequency()) {
					return 1;
				} else if (oldWord.getValue().getFrequency() > newWord.getValue().getFrequency()) {
					return -1;
				}
				return 0;
			}
		});
		
		this.wordManage = new LinkedHashMap<String, WordData>();
		int count = 0;
		int countRe = 0;
		for (int index = 0; index < list.size(); index++) {
			if (index %2 == 0) {
				this.wordManage.put(list.get(count).getKey(), list.get(count).getValue());
				count++;
			} else if (index %2 == 1) {
				this.wordManage.put(listRe.get(countRe).getKey(), listRe.get(countRe).getValue());
				countRe++;
			}
		}
		System.out.println(list.size() + "   ");

		for (Map.Entry<String, WordData> entry : list) {
			System.out.println(entry.getValue().getFrequency());
		}for (Map.Entry<String, WordData> entry : listRe) {
			System.out.println(entry.getValue().getFrequency());
		}
		return this.wordManage;
		
	}
}
