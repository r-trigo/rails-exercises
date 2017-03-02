class Pkm < ApplicationRecord
  extend FriendlyId
  friendly_id :name
  belongs_to :region

  #getter
  def region_name
    region.try(:name)
  end

  #setter
  def region_name=(name)
    self.region = Region.find_by_name(name) if name.present?
  end

  def self.search(search)
    if search
      where('comp_id LIKE ?', "%#{search}%")
    else
      all
    end

    # if region_id && region_id != ''
    #   Pkm.where(['region_id = ?', region_id])
    # else
    #   Pkm.all
    # end
  end

  def self.import(file)
    spreadsheet = open_spreadsheet(file)
    header = spreadsheet.row(1)
    (2 .. spreadsheet.last_row).each do |i|
      row = Hash[[header, spreadsheet.row(i)].transpose]
      pkm = find_or_create_by(comp_id: row['comp_id'])
      pkm.attributes = { comp_id: row['comp_id'], name: row['name'], region_id: row['region_id'] }
      pkm.save!
    end
  end

  def self.open_spreadsheet(file)
    case File.extname(file.original_filename)
      when '.csv' then Roo::Csv.new(file.path, packed: nil, file_warning: :ignore)
      when '.xls' then Roo::Excel.new(file.path, packed: nil, file_warning: :ignore)
      when '.xlsx' then Roo::Excelx.new(file.path, packed: nil, file_warning: :ignore)
      else raise 'Unkown file type: #{file.original_filename}'
    end
  end

end