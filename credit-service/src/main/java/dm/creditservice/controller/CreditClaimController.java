package dm.creditservice.controller;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.mapper.CreditClaimMapper;
import dm.creditservice.payload.CreateCreditClaimRequest;
import dm.creditservice.payload.CreditClaimResponse;
import dm.creditservice.payload.UpdateStatusRequest;
import dm.creditservice.service.CreditClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credit-claim")
public class CreditClaimController {
    private final CreditClaimService creditClaimService;
    private final CreditClaimMapper creditClaimMapper;

    @PostMapping()
    public ResponseEntity<CreditClaimResponse> saveCreditClaim(@RequestBody CreateCreditClaimRequest createCreditClaimRequest) {
        CreditClaimEntity claimEntity = creditClaimService.createCreditClaim(createCreditClaimRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditClaimMapper.toDto(claimEntity));
    }

    @GetMapping()
    public ResponseEntity<List<CreditClaimResponse>> getCreditClaims() {
        List<CreditClaimResponse> claims = creditClaimService.getCreditClaims().stream()
                .map(creditClaimMapper::toDto)
                .toList();

        return ResponseEntity.ok(claims);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditClaimResponse> updateStatus(@PathVariable UUID id, @RequestBody UpdateStatusRequest request) {
        CreditClaimEntity claimEntity = creditClaimService.updateCreditClaimStatus(id, ClaimStatus.valueOf(request.status().toUpperCase()));
        return ResponseEntity.ok(creditClaimMapper.toDto(claimEntity));
    }
}

