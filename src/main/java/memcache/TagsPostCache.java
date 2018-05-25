package memcache;

import model.tags.Tags;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TagsPostCache {

    public static TagsPostCache getInstance() {
        return instance;
    }

    private static final TagsPostCache instance = new TagsPostCache ();

    private final Map<UUID, ArrayList<String>> mapTags;

    private TagsPostCache() {
        mapTags = new ConcurrentHashMap<>();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add("Dogs");
        tagsList.add("Cats");
        tagsList.add("Birds");
        tagsList.add("Rodents");
        tagsList.add("Fish");
        tagsList.add("Farm animals");
        tagsList.add("Other animals");
        Tags tags = new Tags("Types", tagsList);
        mapTags.put(tags.getId(), tagsList);



    }


    public void addTagsList(UUID idTagsList, ArrayList<String> tags){
        mapTags.put(idTagsList, tags);
    }

    public ArrayList<String> getTagsListByIdTagsList(UUID idTagsList){
        return mapTags.get(idTagsList);
    }

    public Set<UUID> getAllIdTagsList(){
        return mapTags.keySet();
    }

    public void deleteTagsListByIdTagsList(UUID idTagsList){
        mapTags.remove(idTagsList);
    }

}
