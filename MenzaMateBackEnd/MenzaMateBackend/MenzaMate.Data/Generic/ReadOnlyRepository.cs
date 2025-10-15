using MenzaMate.Data.Stores;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Data.Generic
{
    public class ReadOnlyRepository<TEntity> : IReadOnlyRepository<TEntity>
        where TEntity : class
    {
        protected readonly MenzaMateContext _context;

        public ReadOnlyRepository(MenzaMateContext context)
        {
            _context = context;
        }

        public IQueryable<TEntity> GetAll()
        {
            return _context.Set<TEntity>().AsNoTracking();
        }

        public async Task<TEntity> GetByIdAsync(int id)
        {
            _context.ChangeTracker.QueryTrackingBehavior = QueryTrackingBehavior.NoTracking;
            return await _context.Set<TEntity>().FindAsync(id);
        }
    }
}
