using MenzaMate.Business.Models.ModelsUser;

namespace MenzaMate.Business.Services.INameService
{
    public interface IGoogleAuthService
    {
        Task<GoogleUserPayload?> ValidateTokenAsync(string idToken);
    }
}
