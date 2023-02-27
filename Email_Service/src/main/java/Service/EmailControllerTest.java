package Service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailController emailController;

    @Test
    void getEmail() {
        Email email = new Email();
        email.setId(1L);
        when(emailRepository.findById(1L)).thenReturn(Optional.of(email));
        assertEquals(email, emailController.getEmail(1L));
    }

    @Test
    void getAllEmails() {
        List<Email> emailList = new ArrayList<>();
        emailList.add(new Email());
        when(emailRepository.findAll()).thenReturn(emailList);
        assertEquals(emailList, emailController.getAllEmails());
    }

    @Test
    void addEmail() {
        Email email = new Email();
        email.setId(1L);
        when(emailRepository.save(any(Email.class))).thenReturn(email);
        assertEquals(email, emailController.addEmail(email));
    }

    @Test
    void updateEmail() {
        Email email = new Email();
        email.setId(1L);
        when(emailRepository.findById(1L)).thenReturn(Optional.of(email));
        when(emailRepository.save(any(Email.class))).thenReturn(email);
        assertEquals(email, emailController.updateEmail(1L, email));
    }

    @Test
    void deleteEmail() {
        emailController.deleteEmail(1L);
        verify(emailRepository, times(1)).deleteById(1L);
    }
}
