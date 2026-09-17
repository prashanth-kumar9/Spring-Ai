package ai.spring.project.aicontroller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller 
public class AiController {
    

    private ChatClient chatClient;

    public AiController(OllamaChatModel chatModel){
        this.chatClient = ChatClient.create(chatModel);
    }

    
    @GetMapping("/{message}")
    public String summary(@PathVariable  String message, Model theModel) {
        String message1 = chatClient.prompt(message).call().content();
        theModel.addAttribute("summary", message1);

        return "response";
    }

    @GetMapping("/hello")
	public String sayHello(Model theModel) {
		
		theModel.addAttribute("summary", java.time.LocalDateTime.now());
		
		return "response";
	}

    @GetMapping("/show")
	public String show() {
		
		//theModel.addAttribute("summary", java.time.LocalDateTime.now());
		
		return "show it is working";
	}
    


}
