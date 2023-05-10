package ch.walkingfish.walkingfish.consumer.tools;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;

public class ArticleDeserializer extends JsonDeserializer<Article> {

    @Override
    public Article deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        String name = node.get("name").asText();
        String description = node.get("description").asText();
        Double price = node.get("price").asDouble();
        String type = node.get("type").asText();

        ArrayList<String> sizes = new ArrayList<>();

        // Try to get the sizes as an array
        JsonNode sizesNode = node.get("sizes");
        
        if (sizesNode != null && sizesNode.isArray()) {
            for (JsonNode sizeNode : sizesNode) {
                sizes.add(sizeNode.asText());
            }
        }

        ArrayList<Colori> coloris = new ArrayList<>();

        JsonNode colorisNode = node.get("coloris");

        if (colorisNode != null && colorisNode.isArray()) {
            for (JsonNode coloriNode : colorisNode) {
                Colori coloriObj = codec.treeToValue(coloriNode, Colori.class);
                coloris.add(coloriObj);
            }
        }

        return new Article(name, description, price, type, sizes, coloris);
    }

    // Example of a JSON to deserialize
    // {
    //     "name": "T-shirt",
    //     "description": "Un t-shirt",
    //     "price": 20.0,
    //     "type": "T-shirt",
    //     "sizes": ["XS", "S", "M", "L", "XL", "XXL", "Taille unique"],
    //     "coloris": [
    //         {
    //             "name": "Rouge",
    //             "hex": "#FF0000"
    //         },
    //         {
    //             "name": "Bleu",
    //             "hex": "#0000FF"
    //         }
    //     ]
    // }

}

