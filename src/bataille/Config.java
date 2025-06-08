package bataille;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Properties;
import bataille.exception.ConfigParseError;

public class Config {
    private Properties properties;
    
    /**
     * Obtenir les configurations du fichier config.properties;
     * @throws Exception dans le cas ou le fichiers n'est pas trouvé, ou si des valeurs sont incoherente;
     */
    public Config() throws Exception{
        //load of config
        InputStream inputStream = new FileInputStream("config.properties"); //ouverture du fichier
        properties = new Properties(); //creation de la liste des propriétés
        properties.load(inputStream);
        Integer nbBateau;
        try {
            nbBateau = Integer.parseInt(properties.getProperty("nbBateau"));
        } catch (Exception e) {
            throw new ConfigParseError("La valeur nbBateau n'est pas un entier");
        }

        if(nbBateau != properties.getProperty("tailleBateau").split(",").length){
            throw new ConfigParseError("Il n'y a pas le bon nombre d'elements dans tailleBateau (config) :" + nbBateau + " != tailleBateau.length");
        }
       
    }


    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé;
     * @return {@linkplain String}; 
     * @throws ConfigParseError si la valeur n'est pas de type {@linkplain String} 
     */
    public String getString(String value) throws ConfigParseError{
        try {
            return properties.getProperty(value);
        } catch (Exception e) {
            throw new ConfigParseError("La valeur de "+ value + " est incorrect ou n'est pas set: "+e);
        }
    }

    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé sous forme de {@linkplain Integer};
     * @return {@linkplain Integer};
     * @throws ConfigParseError si la valeur obtenu n'est pas un entier;
     */
    public Integer getInteger(String value) throws ConfigParseError{
        try {
            return Integer.parseInt(getString(value));
        } catch (Exception e) {
            throw new ConfigParseError("La valeur de "+ value + " est incorrect ou n'est pas set: "+e);
        }
    }

    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé sous forme de {@linkplain Double};
     * @return {@linkplain Integer};
     * @throws ConfigParseError si la valeur obtenu n'est pas un entier;
     */
    public Double getDouble(String value) throws ConfigParseError{
        try {
            return Double.parseDouble(getString(value));
        } catch (Exception e) {
            throw new ConfigParseError("La valeur de "+ value + " est incorrect ou n'est pas set: "+e);
        }
    }

    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé sous forme de {@linkplain Integer};
     * @return {@linkplain Integer};
     * @throws ConfigParseError si la valeur obtenu n'est pas un entier;
     */
    public Integer getPourcentage(String value) throws ConfigParseError{
        try {
            Integer val = Integer.parseInt(getString(value));
            if(val < 0 || val > 100) throw new Exception("Valeur superieur a 100% ou inferieur a 0%"); // Si ce n'est pas un pourcentage
            return val;
        } catch (Exception e) {
            throw new ConfigParseError("La valeur de "+ value + " est incorrect ou n'est pas set: "+e);
        }
    }

    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé sous forme de {@linkplain ArrayList};
     * @param integer est de type {@linkplain Boolean}, permet de savoir si les valeurs souhaitees sont des entiers ou des Strings;
     * <p>{@code integer= true => return ArrayList<Integer>}</p>
     * <p>{@code integer= false => return ArrayList<String>}</p>
     * @return {@linkplain ArrayList} d' {@linkplain Object} (ou {@linkplain Object} = {@linkplain String} || {@linkplain Integer})
     * @throws ConfigParseError dans le cas ou la valeur demandé ne peut etre recuperé sous forme de ArrayList
     */
    public ArrayList<Object> getList(String value, Boolean integer) throws ConfigParseError{
        try {
            ArrayList<Object> lst = new ArrayList<>();
            for (String o : getString(value).split(",")) {
                if (integer == true){
                    lst.add(Integer.parseInt(o));
                }else{
                    lst.add(o);
                }
            }
            return lst;

        } catch (Exception e) {
            throw new ConfigParseError("La valeur de "+ value + " est incorrect ou n'est pas set: "+e);
        }
    }

    //SURCHARGE
    /**
     * @param value est de type {@linkplain String}, permet d'obtenir la valeur demandé sous forme de {@linkplain ArrayList};
     * @return {@linkplain ArrayList} d'{@linkplain Object} (Ou {@linkplain Object} = {@linkplain String});
     * @throws ConfigParseError dans le cas ou la valeur demandé ne peut etre recuperé sous forme de ArrayList
     */
    public ArrayList<Object> getList(String value) throws ConfigParseError{
        return getList(value, false);
    }
    
}
