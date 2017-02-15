class CreateArmarios < ActiveRecord::Migration[5.0]
  def change
    create_table :armarios do |t|
      t.string :ci_armario

      t.timestamps
    end
  end
end
