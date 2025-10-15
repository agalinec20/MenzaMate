namespace MenzaMate.Data.Generic
{
    public interface IReadOnlyRepository<TEntity> where TEntity : class
    {
        IQueryable<TEntity> GetAll();

        Task<TEntity> GetByIdAsync(int id);
    }
}
