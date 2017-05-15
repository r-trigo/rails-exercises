class CreatePts < ActiveRecord::Migration[5.0]
  def change
    create_table :pts do |t|
      t.string :ci_pt
      t.string :tipo

      t.timestamps
    end
  end
end
