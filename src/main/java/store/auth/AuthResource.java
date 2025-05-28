package store.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource implements AuthController {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<TokenOut> register(RegisterIn registerIn) {
        Register saved = authService.register(AuthParser.to(registerIn));
        String token = authService.generateToken(saved);
        return ResponseEntity.ok().body(AuthParser.to(saved, token));
    }

    @Override
    public ResponseEntity<TokenOut> login(LoginIn loginIn) {
        Register user = authService.findByEmailAndPassword(loginIn.email(), loginIn.password());
        String token = authService.generateToken(user);
        return ResponseEntity.ok().body(AuthParser.to(user, token));
    }

    @Override
    public ResponseEntity<SolveOut> solve(TokenOut token) {
        return ResponseEntity.ok().body(
            authService.solve(token.token())
        );
    }
}