package software.pinklady.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import java.io.*;
import java.util.Properties;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchDemoApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${myservice.url}")
    private String myserviceUrl;

	@Bean
	public Step step() {
		return this.stepBuilderFactory.get("step1")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution,
							ChunkContext chunkContext) {
								try{
								Thread.sleep(5000);
								System.out.println("Service URL:"+System.getenv("SERVICE_URL"));
								System.out.println("My service URL:"+myserviceUrl);
								try(FileReader f = new FileReader("/config/service.properties")){ 
                                 Properties p = new Properties();
								 p.load(f);
								 System.out.println("Service Properties service1.url "+p.getProperty("service1.url"));
								}
								}
								catch(Exception e){}
								System.out.println("This is where some job will run");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.start(step())
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemoApplication.class, args);
	}
}
