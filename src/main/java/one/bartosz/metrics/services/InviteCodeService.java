package one.bartosz.metrics.services;

import one.bartosz.metrics.exceptions.EntityNotFoundException;
import one.bartosz.metrics.exceptions.InvalidDateException;
import one.bartosz.metrics.models.InviteCode;
import one.bartosz.metrics.models.User;
import one.bartosz.metrics.repositories.InviteCodeRepository;
import one.bartosz.metrics.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class InviteCodeService {

    private final InviteCodeRepository inviteCodeRepository;

    public InviteCodeService(InviteCodeRepository inviteCodeRepository) {
        this.inviteCodeRepository = inviteCodeRepository;
    }

    public InviteCode createInviteCode(User user, long expiryDate) throws InvalidDateException {
        if (expiryDate < Instant.now().getEpochSecond()) throw new InvalidDateException("The provided expiry date is in the past. (has to be epoch second)");
        InviteCode code = new InviteCode().setExpiresOn(expiryDate).setUser(user);
        return inviteCodeRepository.save(code);
    }

    public void deleteInviteCode(User user, String id) throws EntityNotFoundException {
        InviteCode invite = inviteCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invite code with given ID couldn't be found."));
        if (invite.getUser().getId().equals(user.getId())) inviteCodeRepository.delete(invite);
        else throw new EntityNotFoundException("Invite code with given ID couldn't be found.");
    }

    public InviteCode getInviteCodeById(User user, String id) throws EntityNotFoundException {
        InviteCode invite = inviteCodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invite code with given ID couldn't be found."));
        if (invite.getUser().getId().equals(user.getId())) return invite;
        else throw new EntityNotFoundException("Invite code with given ID couldn't be found.");
    }

    public List<InviteCode> getAllInviteCodesByUser(User user) {
        return inviteCodeRepository.findAllByUserId(user.getId());
    }
}
