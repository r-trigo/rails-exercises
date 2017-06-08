class TorneioEquipamento < ApplicationRecord
  has_and_belongs_to_many :torneio_jogador
end
