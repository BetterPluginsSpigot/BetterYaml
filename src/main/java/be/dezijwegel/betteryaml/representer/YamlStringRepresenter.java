package be.dezijwegel.betteryaml.representer;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class YamlStringRepresenter extends Representer
{

    /**
     * Heavily inspired by: https://stackoverflow.com/questions/60501519/missing-double-quotes-for-the-required-field-using-snake-yaml
     */
    public YamlStringRepresenter()
    {
        this.representers.put(String.class, new RepresentYamlString());
    }

    private class RepresentYamlString implements Represent
    {
        public Node representData(Object data)
        {
            String string = (String) data;
            return representScalar(Tag.STR, string, DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        }
    }

}
