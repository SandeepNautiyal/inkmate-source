package com.inkmate.app.git;

import com.inkmate.app.exception.GitException;
import com.inkmate.app.exception.ProcessingException;
import com.inkmate.app.process.FullTextSearchQueryProcessor;
import com.inkmate.app.service.GitService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class GitReader implements GitService {

    private Repository repository = null;

    private ContentsService contentService = null;

    @Autowired
    private FullTextSearchQueryProcessor processor;

    @PostConstruct
    public void initRepo() throws GitException {
        try {
            String gitToken  = processor.getGitToken().getGitToken();
            System.out.println("git token =============================== "+gitToken);
            GitHubClient client = new GitHubClient().setCredentials("SandeepNautiyal", gitToken);
            RepositoryService repoService = new RepositoryService(client);
            repository = repoService.getRepository("SandeepNautiyal", "inkmatecodingproblems");
            contentService = new ContentsService(client);
        } catch (ProcessingException | IOException e) {
            throw new GitException("Unable to read from Repo", e);
        }
    }

    @Override
    public void readFromGit() throws IOException {
        List<RepositoryContents> repoContent = contentService.getContents(repository);
        for (RepositoryContents content : repoContent) {
            String fileContent = content.getContent();
            List<RepositoryContents> test = contentService.getContents(repository, content.getName());
            for (RepositoryContents c : test) {
                String fileConent = c.getContent();
                String valueDecoded = new String(Base64.decodeBase64(fileConent.getBytes()));
                System.out.println("value  = ===============" + valueDecoded);
            }
        }
    }

    @Override
    public String readFromGit(String fileName) throws GitException {
        StringBuilder sb = new StringBuilder();
        try{
            List<RepositoryContents> contents = contentService.getContents(repository, fileName);
            for (RepositoryContents c : contents) {
                String fileConent = c.getContent();
                String valueDecoded = new String(Base64.decodeBase64(fileConent.getBytes()));
                sb.append(valueDecoded);
            }
        }
        catch(IOException e){
            throw new GitException("Unable to read file from Git");
        }
        return sb.toString();
    }
}
