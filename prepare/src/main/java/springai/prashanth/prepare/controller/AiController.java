package springai.prashanth.prepare.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springai.prashanth.prepare.model.ActorList;


@RestController 
public class AiController {
    

    private final ChatClient chatClient;


    public AiController(ChatClient.Builder chatClientBuilder){
        this.chatClient= chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue  ="who are you" ) String message) {


        return chatClient.prompt().user(message).call().content();
    }

    @GetMapping("/prompt-eng")
    public String promptEng(@RequestParam(defaultValue  ="java" ) String topic) {

        SystemMessage systemMessage =new SystemMessage("you are a helpful teacher. You explain topic given to you in detailed manner");

        UserMessage userMessage = new UserMessage("Explain the topic" + topic +"in details");

        ChatOptions options = ChatOptions.builder().maxTokens(100).build();

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), options);

        return chatClient.prompt(prompt).call().content();
    }


    @GetMapping("/template")
    public String template(@RequestParam(defaultValue = "Java") String topic) {

        PromptTemplate template = new PromptTemplate("Tell me why to choose {topic} programming");
        String prompt= template.render(Map.of("topic", topic));
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/actor-films")
    public String actorfilms(@RequestParam(defaultValue = "JR.NTR") String actoString) {

        BeanOutputConverter<ActorList> converter = new BeanOutputConverter<>(ActorList.class);
        String fomat =converter.getFormat();
        PromptTemplate acPromptTemplate = new PromptTemplate("list 5 films of the actor {actor} in the following {fomat}");
        String promString = acPromptTemplate.render(Map.of("actor" , actoString, "fomat", fomat));
        String  response=  chatClient.prompt(promString).call().content();

        return response;
    }
    
    
    
    
}
