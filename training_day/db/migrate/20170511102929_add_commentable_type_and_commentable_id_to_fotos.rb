class AddCommentableTypeAndCommentableIdToFotos < ActiveRecord::Migration[5.0]
  def change
    add_reference :fotos, :imageable, polymorphic: true, index: true
    remove_column :fotos, :armario_id
    #implicito
    #remove_index :fotos, :armario_id
  end
end
