package one.bartosz.metrics.controllers;

import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.InvalidDateException;
import one.bartosz.metrics.models.InviteCode;
import one.bartosz.metrics.models.Response;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.services.InviteCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invite")
public class InviteCodeController {

    private final InviteCodeService inviteCodeService;

    public InviteCodeController(InviteCodeService inviteCodeService) {
        this.inviteCodeService = inviteCodeService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Response> createInviteCode(@AuthenticationPrincipal User user, @RequestParam long expiryDate) throws InvalidDateException {
        InviteCode inviteCode = inviteCodeService.createInviteCode(user, expiryDate);
        return new Response(HttpStatus.CREATED).addAdditionalData(inviteCode).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> deleteInviteCode(@AuthenticationPrincipal User user, @PathVariable String id) throws EntityNotFoundException {
        inviteCodeService.deleteInviteCode(user, id);
        return new Response(HttpStatus.NO_CONTENT).toResponseEntity();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Response> getInviteCodeById(@AuthenticationPrincipal User user, @PathVariable String id) throws EntityNotFoundException {
        InviteCode inviteCodeById = inviteCodeService.getInviteCodeById(user, id);
        return new Response(HttpStatus.OK).addAdditionalData(inviteCodeById).toResponseEntity();
    }

    @DeleteMapping("")
    @ResponseBody
    public ResponseEntity<Response> getAllUserInviteCodes(@AuthenticationPrincipal User user) {
        List<InviteCode> allInviteCodesByUser = inviteCodeService.getAllInviteCodesByUser(user);
        return new Response(HttpStatus.OK).addAdditionalData(allInviteCodesByUser).toResponseEntity();
    }

}
